#!/bin/bash

cd src/c

asn1c -fcompound-names -gen-PER -gen-OER -pdu=all \
    ../asn1/1609dot2-base-types.asn \
    ../asn1/1609dot2-schema.asn \
    ../asn1/J2735_201603_ASN.asn  \
    ../asn1/SEMI_v2.3.0_070616.asn \
    2>&1 | tee compile.out

rm Makefile.am.example
rm converter-example.c
