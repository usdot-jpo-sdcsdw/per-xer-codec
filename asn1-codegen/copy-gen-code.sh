#!/bin/bash

# Copy generated h and c files
cp src/c/*.{h,c} ../jni/asn1/
# Copy the makefile (probably not necessary anymore)
cp src/c/Makefile.am.libasncodec ../jni/asn1
# Workaround: copy the asn_application files from the skeletons which for some reason aren't being used by the generator
cp asn1/skeletons/asn_application.* ../jni/asn1
