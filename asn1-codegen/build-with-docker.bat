SET IMAGE_NAME=asn1-codegen-build-box
SET CONTAINER_NAME=asn1-codegen-build-box
SET CONTAINER_WORK_DIR=/opt/asn1-codegen
SET TARGET_DIR=src\c
SET INSTALL_DIR=..\native\src\main\c\asn1
SET CC=clang

docker build^
    -t %IMAGE_NAME%^
    -f ./Dockerfile^
    --build-arg http_proxy=%HTTP_PROXY%^
    --build-arg https_proxy=%HTTPS_PROXY%^
    ..

SET RETURN_CODE=%ERRORLEVEL%
IF /I "%RETURN_CODE%" NEQ "0" (
    EXIT /B %RETURN_CODE%
)

docker run^
    --name %CONTAINER_NAME%^
    %IMAGE_NAME%^
    make CC=%CC%

SET RETURN_CODE=%ERRORLEVEL%
IF /I "%RETURN_CODE%" NEQ "0" (
    EXIT /B %RETURN_CODE%
)

docker cp^
    %CONTAINER_NAME%:%CONTAINER_WORK_DIR%\%TARGET_DIR%\.^
    %TARGET_DIR%\

SET BUILD_AND_COPY_RETURN_CODE=%ERRORLEVEL%

docker rm %CONTAINER_NAME%

SET RETURN_CODE=%ERRORLEVEL%

IF /I "%BUILD_AND_COPY_RETURN_CODE%" NEQ "0" (
    EXIT /B %BUILD_AND_COPY_RETURN_CODE%
)

XCOPY /s /e /y %TARGET_DIR%/. %INSTALL_DIR%/

EXIT /B %RETURN_CODE%