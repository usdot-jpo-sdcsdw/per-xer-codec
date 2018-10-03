SET IMAGE_NAME=libper-xer-codec-build-box
SET CONTAINER_NAME=libper-xer-codec-build-box
SET CONTAINER_WORK_DIR=/opt/per-xer-codec/
SET LIBRARY_NAME=libper-xer-codec.so
SET MAVEN_ARTIFACT_NAME=per-xer-codec-native-linux.so
SET TARGET_DIR=target
SET CC=clang

docker build^
    -t %IMAGE_NAME%^
    -f ./Dockerfile^
    --build-arg http_proxy=%HTTP_PROXY%^
    --build-arg https_proxy=%HTTPS_PROXY%^
    ..

SET RETURN_CODE=%ERROR_LEVEL%
IF /I "%RETURN_CODE%" NEQ "0" (
    EXIT /B %RETURN_CODE%
)

docker run^
    --name %CONTAINER_NAME%^
    %IMAGE_NAME%^
    make CC=%CC% -C native/
IF /I "%ERROR_LEVEL%" EQ "0" (
docker cp^
    %CONTAINER_NAME%:%CONTAINER_WORK_DIR%/native/%TARGET_DIR%/%LIBRARY_NAME%^
    %TARGET_DIR%/%LIBRARY_NAME%
)

SET BUILD_AND_COPY_RETURN_CODE=%ERROR_LEVEL%

docker rm %CONTAINER_NAME%

SET RETURN_CODE=%ERROR_LEVEL%

IF /I "%BUILD_AND_COPY_RETURN_CODE%" NEQ "0" (
    EXIT /B %BUILD_AND_COPY_RETURN_CODE%
)

COPY %TARGET_DIR%\%LIBRARY_NAME% %TARGET_DIR%\%MAVEN_ARTIFACT_NAME%

EXIT /B %RETURN_CODE%