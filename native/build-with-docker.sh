set -x

IMAGE_NAME=libper-xer-codec-build-box
CONTAINER_NAME=libper-xer-codec-build-box
CONTAINER_WORK_DIR=/opt/per-xer-codec/
LIBRARY_NAME=libper-xer-codec.so
MAVEN_ARTIFACT_NAME=per-xer-codec-native-linux.so
TARGET_DIR=target
CC=clang

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
    make CC=$CC -C native/ \
&& docker cp \
    $CONTAINER_NAME:$CONTAINER_WORK_DIR/native/$TARGET_DIR/$LIBRARY_NAME \
    $TARGET_DIR/$LIBRARY_NAME

BUILD_AND_COPY_RETURN_CODE=$?

docker rm $CONTAINER_NAME

RETURN_CODE=$?

if [ "$BUILD_AND_COPY_RETURN_CODE" -ne "0" ]; then
    exit $BUILD_AND_COPY_RETURN_CODE
fi

cp $TARGET_DIR/$LIBRARY_NAME $TARGET_DIR/$MAVEN_ARTIFACT_NAME

exit $RETURN_CODE