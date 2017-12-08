from debian:latest

RUN apt-get update && apt-get install -y build-essential openjdk-8-jdk

COPY Linux/ /opt/per-xer-codec/Linux

COPY jni/ opt/per-xer-codec/jni

WORKDIR /opt/per-xer-codec/Linux

RUN make clean

RUN sed -i 's/\/Users\/amm30955\/backup-restore\/Users\/amm30955\/Documents\/workspace\/DOT/\/opt/g' jni/subdir.mk && cat jni/subdir.mk

RUN sed -i 's/\/Users\/amm30955\/backup-restore\/Users\/amm30955\/Documents\/workspace\/DOT/\/opt/g' jni/asn1/subdir.mk && cat jni/asn1/subdir.mk

RUN sed -i 's/\/Users\/amm30955\/backup-restore\/Users\/amm30955\/Documents\/workspace\/DOT/\/opt/g' makefile && cat makefile

RUN ln -s /usr/lib/jvm/java-1.8.0-openjdk-amd64 /usr/lib/jvm/default-java

RUN make
