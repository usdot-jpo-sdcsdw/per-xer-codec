set -x

IMAGE_NAME=libper-xer-codec-build-box
CONTAINER_NAME=libper-xer-codec-build-box
CONTAINER_WORK_DIR=/opt/per-xer-codec/

docker build -t $IMAGE_NAME --build-arg http_proxy=$HTTP_PROXY --build-arg https_proxy=$HTTPS_PROXY .

#mv ../target/libper-xer-codec.so ../target/.libper-xer-codec.bak
#mv target/libper-xer-codec.so target/.libper-xer-codec.bak

docker run \
    --name $CONTAINER_NAME \
    -v $PWD/../../:$CONTAINER_WORK_DIR \
    $IMAGE_NAME \
    make -C native/

RETURN_CODE=$?

docker rm $CONTAINER_NAME

cp ../target/libper-xer-codec.so target/per-xer-codec-native-docker.so 

exit $RETURN_CODE