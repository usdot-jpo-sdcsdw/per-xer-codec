SET IMAGE_NAME=libper-xer-codec-build-box
SET CONTAINER_NAME=libper-xer-codec-build-box
SET CONTAINER_WORK_DIR=/opt/per-xer-codec/

docker build -t %IMAGE_NAME% --build-arg http_proxy=%HTTP_PROXY% --build-arg https_proxy=%HTTPS_PROXY% .

#mv ..\target\libper-xer-codec.so ..\target\.libper-xer-codec.bak
#mv target\libper-xer-codec.so target\.libper-xer-codec.bak

docker run^
    --name %CONTAINER_NAME%^
    -v %CD%\..\:%CONTAINER_WORK_DIR%^
    %IMAGE_NAME%^
    make -C native/

SET RETURN_CODE=%ERROR_LEVEL%

docker rm %CONTAINER_NAME%

COPY target\libper-xer-codec.so target\per-xer-codec-native-linux.so 

EXIT /B %RETURN_CODE%