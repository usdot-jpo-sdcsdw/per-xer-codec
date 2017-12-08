.PHONY: clean directories install all

ASN1C_HOME=$(PWD)

COMPILER_INSTALL_PREFIX=$(ASN1C_HOME)/install/

GENERATED_C_CODE_INSTALL_PREFIX=$(ASN1C_HOME)/../jni/asn1/

all : directories src/c/Makefile.am.libasncodec

directories :
	mkdir -p $(GENERATED_C_CODE_INSTALL_PREFIX)
	mkdir -p src/c
	mkdir -p src/asn1
	mkdir -p install

src/asn1/1609dot2-base-types.asn :
	test -f src/asn1/1609dot2-base-types.asn || (echo "You forgot to add necessary ASN.1 source files" && exit 1)

src/asn1/1609dot2-schema.asn :
	test -f src/asn1src/asn1/1609dot2-schema.asn || (echo "You forgot to add necessary ASN.1 source files" && exit 1)

src/asn1/J2735_201603_ASN.asn :
	test -f src/asn1/J2735_201603_ASN.asn || (echo "You forgot to add necessary ASN.1 source files" && exit 1)

src/asn1/SEMI_v2.3.0_070616.asn :
	test -f src/asn1/SEMI_v2.3.0_070616.asn || (echo "You forgot to add necessary ASN.1 source files" && exit 1)

asn1c/configure.ac :
	echo "You forgot to clone the asn1c submodule"
	exit -1

asn1c/configure : 
	cd asn1c && autoreconf -iv

asn1c/Makefile : asn1c/configure
	cd asn1c && ./configure --prefix=$(COMPILER_INSTALL_PREFIX)

install/bin/asn1c : asn1c/Makefile
	cd asn1c \
	  && make \
	  && make install

src/c/Makefile.am.libasncodec : install/bin/asn1c src/asn1/1609dot2-base-types.asn src/asn1/1609dot2-schema.asn src/asn1/J2735_201603_ASN.asn src/asn1/SEMI_v2.3.0_070616.asn
	cd src/c \
	  && ../../install/bin/asn1c -fcompound-names -gen-PER -gen-OER -pdu=all \
		../asn1/1609dot2-base-types.asn \
		../asn1/1609dot2-schema.asn \
		../asn1/J2735_201603_ASN.asn  \
		../asn1/SEMI_v2.3.0_070616.asn \
		2>&1 | tee compile.out
	rm -f src/c/converter-example.c

install : all
	install -m 644 src/c/*.{h,c} $(GENERATED_C_CODE_INSTALL_PREFIX)
	# Workaround: copy the asn_application files from the skeletons which for some reason aren't being used by the generator
	install -m 644 asn1c/skeletons/asn_application.{h,c} $(GENERATED_C_CODE_INSTALL_PREFIX)
	install -m 644 asn1c/skeletons/oer_*.{h,c} $(GENERATED_C_CODE_INSTALL_PREFIX)

clean :
	cd asn1c && make clean
	rm -f asn1c/configure
	rm -f asn1c/Makefile
	rm -rf src/c/*
