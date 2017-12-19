IMAGE_NAME=libper-xer-codec-build-box
CONTAINER_NAME=libper-xer-codec-build-box
CONTAINER_WORK_DIR=/opt/per-xer-codec/Linux

docker build -t $IMAGE_NAME --build-arg http_proxy=$HTTP_PROXY --build-arg https_proxy=$HTTPS_PROXY --build-arg PROJECT_ROOT=`dirname $(pwd)` .

mv libper-xer-codec.so .libper-xer-codec.bak

docker run --name $CONTAINER_NAME $IMAGE_NAME

docker cp $CONTAINER_NAME:$CONTAINER_WORK_DIR/libper-xer-codec.so ./libper-xer-codec.so