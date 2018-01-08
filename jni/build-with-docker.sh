set -x

IMAGE_NAME=libper-xer-codec-build-box
CONTAINER_NAME=libper-xer-codec-build-box
CONTAINER_WORK_DIR=/opt/per-xer-codec/

docker build -t $IMAGE_NAME --build-arg http_proxy=$HTTP_PROXY --build-arg https_proxy=$HTTPS_PROXY .

mv libper-xer-codec.so .libper-xer-codec.bak

docker run \
    --name $CONTAINER_NAME \
    -v $PWD:$CONTAINER_WORK_DIR/jni \
    -v $PWD/../target/classes/:$CONTAINER_WORK_DIR/target/classes/ \
    $IMAGE_NAME \
    make -C jni libper-xer-codec.so

#docker cp $CONTAINER_NAME:$CONTAINER_WORK_DIR/jni/libper-xer-codec.so ./libper-xer-codec.so

docker rm $CONTAINER_NAME