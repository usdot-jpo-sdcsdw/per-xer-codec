.PHONY: clean directories install

ASN1C_HOME=`pwd`

COMPILER_INSTALL_PREFIX=$ASN1C_HOME/install/

GENERATED_C_CODE_INSTALL_PREFIX=$ASN1C_HOME/../jni/asn1/

directories :
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

install/bin/asn1c :
	cd asn1c
	(test -f "./configure" || autoreconf -iv)
	./configure --prefix $ICOMPILER_NSTALL_PREFIX
	make
	make install

compile.out : install/bin/asn1c src/asn1/1609dot2-base-types.asn src/asn1/1609dot2-schema.asnsrc/asn1/J2735_201603_ASN.asn src/asn1/SEMI_v2.3.0_070616.asn
	cd src/c
	../../install/bin/asn1c -fcompound-names -gen-PER -gen-OER -pdu=all \
		../asn1/1609dot2-base-types.asn \
		../asn1/1609dot2-schema.asn \
		../asn1/J2735_201603_ASN.asn  \
		../asn1/SEMI_v2.3.0_070616.asn \
		2>&1 | tee compile.out
	rm src/c/Makefile.am.example
	rm src/c/converter-example.c

install : compile.out
	install -m 644 src/c/*.{h,c} ../jni/asn1/
	# Workaround: copy the asn_application files from the skeletons which for some reason aren't being used by the generator
	install -m 644 asn1c/skeletons/asn_application.* $GENERATED_C_CODE_INSTALL_PREFIX

clean :
	rm -rf src/c/*
