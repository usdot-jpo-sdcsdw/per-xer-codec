from debian:latest

RUN apt-get update && apt-get install -y build-essential openjdk-8-jdk

COPY Linux/ /opt/per-xer-codec/Linux

COPY jni/ opt/per-xer-codec/jni

WORKDIR /opt/per-xer-codec/Linux

ARG JAVA_PATH=/usr/lib/jvm/java-1.8.0-openjdk-amd64

ARG PROJECT_ROOT

RUN make clean

RUN sed -i "s|$PROJECT_ROOT|/opt/|g" jni/subdir.mk && cat jni/subdir.mk

RUN sed -i "s|$PROJECT_ROOT|/opt/|g" jni/asn1/subdir.mk && cat jni/asn1/subdir.mk

RUN sed -i "s|$PROJECT_ROOT|/opt/|g" makefile && cat makefile

RUN ln -s $JAVA_PATH /usr/lib/jvm/default-java

RUN make