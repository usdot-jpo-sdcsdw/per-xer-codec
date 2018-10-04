from debian:latest


RUN apt-get update && apt-get install -y build-essential autotools-dev autoconf libtool clang

RUN mkdir -p /opt/

COPY asn1-codegen /opt/asn1-codegen

WORKDIR /opt/asn1-codegen/

RUN make install/bin/asn1c
