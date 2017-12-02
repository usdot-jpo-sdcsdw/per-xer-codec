/*
#include "asn1/xer_decoder.h"
#include "asn1/xer_encoder.h"
#include "asn1/per_decoder.h"
#include "asn1/per_encoder.h"
*/

#include <stdint.h>

#include <jni.h>

#include "gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native.h"

#include "asn1/asn_application.h"
#include "asn1/AdvisorySituationData.h"
#include "asn1/ServiceRequest.h"
#include "asn1/ServiceResponse.h"
#include "asn1/DataRequest.h"
#include "asn1/AdvisorySituationDataDistribution.h"
#include "asn1/DataAcceptance.h"
#include "asn1/DataReceipt.h"

#include <stdio.h>

enum asn1Type
{
	ASN1TYPE_ADVISORY_SITUATION_DATA = 0,
	ASN1TYPE_SERVICE_REQUEST = 1,
	ASN1TYPE_SERVICE_RESPONSE = 2,
	ASN1TYPE_DATA_REQUEST = 3,
	ASN1TYPE_ADVISORY_SITUATION_DATA_DISTRIBUTION = 4,
	ASN1TYPE_DATA_ACCEPTANCE = 5,
	ASN1TYPE_DATA_RECEIPT = 6
};

int xer_to_per_c(const struct asn_codec_ctx_s *opt_codec_ctx,
                  const struct asn_TYPE_descriptor_s *type_descriptor,
                  const void *input_buffer,
                  size_t input_size,
                  void **output_buffer_ptr,
                  size_t *output_size_ptr,
                  asn_dec_rval_t *dec_rval,
                  asn_enc_rval_t *enc_rval)
{
    void* intermediate = NULL;
    
    *output_buffer_ptr = NULL;

    *dec_rval = asn_decode(opt_codec_ctx, ATS_CANONICAL_XER, type_descriptor, &intermediate, input_buffer, input_size);
    if (dec_rval->code != RC_OK)
    		return -1;

    asn_encode_to_new_buffer_result_t enc_result = asn_encode_to_new_buffer(opt_codec_ctx, ATS_UNALIGNED_BASIC_PER, type_descriptor, intermediate);

    *output_buffer_ptr = enc_result.buffer;
    *enc_rval = enc_result.result;
    *output_size_ptr = enc_rval->encoded;

    free(intermediate);

    if (enc_rval->encoded == -1)
    		return -1;
    else
    		return 0;
}

int per_to_xer_c(const struct asn_codec_ctx_s *opt_codec_ctx,
                  const struct asn_TYPE_descriptor_s *type_descriptor,
                  const void *input_buffer,
                  size_t input_size,
                  void **output_buffer_ptr,
                  size_t *output_size_ptr,
                  asn_dec_rval_t *dec_rval,
                  asn_enc_rval_t *enc_rval)
{
	void* intermediate = NULL;

	*output_buffer_ptr = NULL;

	*dec_rval = asn_decode(opt_codec_ctx, ATS_UNALIGNED_BASIC_PER, type_descriptor, &intermediate, input_buffer, input_size);
	if (dec_rval->code != RC_OK)
			return -1;

	asn_encode_to_new_buffer_result_t enc_result = asn_encode_to_new_buffer(opt_codec_ctx, ATS_CANONICAL_XER, type_descriptor, intermediate);

	*output_buffer_ptr = enc_result.buffer;
	*enc_rval = enc_result.result;

	free(intermediate);

	if (enc_rval->encoded == -1)
			return -1;
	else
			return 0;
}


struct asn_TYPE_descriptor_s *pick_type(enum asn1Type type) {
	switch (type)
	{
	case ASN1TYPE_ADVISORY_SITUATION_DATA:
		return &asn_DEF_AdvisorySituationData;
	case ASN1TYPE_SERVICE_REQUEST:
		return &asn_DEF_ServiceRequest;
	case ASN1TYPE_SERVICE_RESPONSE:
		return &asn_DEF_ServiceResponse;
	case ASN1TYPE_DATA_REQUEST:
		return &asn_DEF_DataRequest;
	case ASN1TYPE_ADVISORY_SITUATION_DATA_DISTRIBUTION:
		return &asn_DEF_AdvisorySituationDataDistribution;
	case ASN1TYPE_DATA_ACCEPTANCE:
		return &asn_DEF_DataAcceptance;
	case ASN1TYPE_DATA_RECEIPT:
		return &asn_DEF_DataReceipt;
	default:
		return NULL;
	}
}

JNIEXPORT jbyteArray JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_xerToPer(JNIEnv* env, jclass myClass, jint type, jstring xer)
{
	// Create dummy context
	// TODO: accept this as an argument?
    struct asn_codec_ctx_s dummy_context = { .max_stack_size = 0 }; // No stack limit
    
    // Create a C string from the XER Java String
    const char *xer_c = (*env)->GetStringUTFChars(env, xer, NULL);
    size_t xer_c_size = (*env)->GetStringLength(env, xer);
    void* per_c = NULL;
    size_t per_c_size = 0;
    asn_dec_rval_t dec_rval;
    asn_enc_rval_t enc_rval;

    jbyteArray per = NULL;

    // Determine which type the user wants
    struct asn_TYPE_descriptor_s *type_descriptor = pick_type(type);



    // If the type is valid, attempt to decode and re-encode

    if (type_descriptor != NULL) {
		if (xer_to_per_c(&dummy_context, type_descriptor, xer_c, xer_c_size, &per_c, &per_c_size, &dec_rval, &enc_rval) == 0) {
				// If successful, copy the PER c byte array into the java byte array
				per = (*env)->NewByteArray(env, per_c_size);
				(*env)->SetByteArrayRegion(env, per, 0, per_c_size, per_c);
		}
    }

    // Release the c string array we got from the Java XER string

    (*env)->ReleaseStringUTFChars(env, xer, xer_c);

    // Return the java byte array PER, which is null if we failed

    return per;
}

JNIEXPORT jstring JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_perToXer(JNIEnv* env, jclass myClass, jint type, jbyteArray per)
{

	// Create dummy context
	// TODO: accept this as an argument?
    struct asn_codec_ctx_s dummy_context = { .max_stack_size = 0 }; // No stack limit

    int8_t *per_c = (*env)->GetByteArrayElements(env, per, NULL);
    size_t per_c_size = (*env)->GetArrayLength(env, per);
    char *xer_c = NULL;
    size_t xer_c_size = 0;
    asn_dec_rval_t dec_rval;
    asn_enc_rval_t enc_rval;

    jstring xer = NULL;

    // Determine which type the user wants
	struct asn_TYPE_descriptor_s *type_descriptor = pick_type(type);

	// If the type is valid, attempt to decode and re-encode

	if (type_descriptor != NULL) {

		if (per_to_xer_c(&dummy_context, type_descriptor, per_c, per_c_size, &xer_c, &xer_c_size, &dec_rval, &enc_rval) == 0) {
				// If successful, copy the PER c byte array into the java byte array

				xer = (*env)->NewStringUTF(env, xer_c);
		}
	}

	(*env)->ReleaseByteArrayElements(env, per, per_c, JNI_ABORT);


	return xer;
}

JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getAdvisorySituationDataType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_ADVISORY_SITUATION_DATA;
}



JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getServiceRequestType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_SERVICE_REQUEST;
}

JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getServiceResponseType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_SERVICE_RESPONSE;
}

JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getDataRequestType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_DATA_REQUEST;
}

JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getAdvisorySituationDataDistributionType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_ADVISORY_SITUATION_DATA_DISTRIBUTION;
}


JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getDataAcceptanceType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_DATA_ACCEPTANCE;
}

JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getDataReceiptType(JNIEnv * env, jclass myClass)
{
	return ASN1TYPE_DATA_RECEIPT;
}
