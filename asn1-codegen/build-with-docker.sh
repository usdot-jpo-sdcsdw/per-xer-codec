set -x

IMAGE_NAME=asn1-codegen-build-box
CONTAINER_NAME=asn1-codegen-build-box
CONTAINER_WORK_DIR=/opt/asn1-codegen
TARGET_DIR=src/c
INSTALL_DIR=../native/src/main/c/asn1
CC=clang

case $(uname) in
    MINGW*)
        git -C asn1c checkout skeletons/asn_system.h
        git -C asn1c apply $(realpath asn_system.h.patch)
        ;;
esac

docker build \
    -t $IMAGE_NAME \
    -f ./Dockerfile \
    --build-arg http_proxy=$HTTP_PROXY \
    --build-arg https_proxy=$HTTPS_PROXY \
    ..

RETURN_CODE=$?
if [ "$RETURN_CODE" -ne "0" ]; then
    exit $RETURN_CODE
fi

docker run \
    --name $CONTAINER_NAME \
    $IMAGE_NAME \
    make CC=$CC \
&& docker cp \
    $CONTAINER_NAME:$CONTAINER_WORK_DIR/$TARGET_DIR/. \
    $TARGET_DIR/

BUILD_AND_COPY_RETURN_CODE=$?

docker rm $CONTAINER_NAME

RETURN_CODE=$?

if [ "$BUILD_AND_COPY_RETURN_CODE" -ne "0" ]; then
    exit $BUILD_AND_COPY_RETURN_CODE
fi

cp -R $TARGET_DIR/. $INSTALL_DIR/

exit $RETURN_CODE
