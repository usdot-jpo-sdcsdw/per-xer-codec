#!/bin/bash

ASN1C_HOME=`pwd`

INSTALL_PREFIX=$ASN1C_HOME/install

cd asn1c && (test -f "./configure --prefix $INSTALL_PREFIX" || autoreconf -iv) && ./configure --prefix $INSTALL_PREFIX && make && make install
