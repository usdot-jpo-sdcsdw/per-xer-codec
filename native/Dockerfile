from debian:latest

ARG JAVA_PATH=/usr/lib/jvm/java-1.8.0-openjdk-amd64

WORKDIR /opt/per-xer-codec/

RUN apt-get update && apt-get install -y build-essential clang openjdk-8-jdk

RUN ln -s $JAVA_PATH /usr/lib/jvm/default-java

COPY java java
COPY native native