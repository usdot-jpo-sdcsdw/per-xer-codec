from debian:latest

RUN apt-get update && apt-get install -y build-essential openjdk-8-jdk

VOLUME /opt/per-xer-codec/

WORKDIR /opt/per-xer-codec/

ARG JAVA_PATH=/usr/lib/jvm/java-1.8.0-openjdk-amd64

RUN ln -s $JAVA_PATH /usr/lib/jvm/default-java