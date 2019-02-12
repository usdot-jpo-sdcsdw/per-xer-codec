/*
#include "asn1/xer_decoder.h"
#include "asn1/xer_encoder.h"
#include "asn1/per_decoder.h"
#include "asn1/per_encoder.h"
*/

#include "gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native.h"

#include <stdint.h>

#include <jni.h>

#include "asn1/asn_application.h"
#include "asn1/AdvisorySituationData.h"
#include "asn1/ServiceRequest.h"
#include "asn1/ServiceResponse.h"
#include "asn1/DataRequest.h"
#include "asn1/AdvisorySituationDataDistribution.h"
#include "asn1/AdvisorySituationBundle.h"
#include "asn1/DataAcceptance.h"
#include "asn1/DataReceipt.h"
#include "asn1/SemiDialogID.h"
#include "asn1/SemiSequenceID.h"

#include <stdio.h>

typedef struct semi_ids_s {
	SemiDialogID_t dialog_id;
	int dialog_id_constrained;
	SemiSequenceID_t seq_id;
	int seq_id_constrained;
} semi_ids_t;

const semi_ids_t AdvisorySituationDataIDs = {
		.dialog_id = 0x9C,
		.dialog_id_constrained = 1,
		.seq_id = 0x05,
		.seq_id_constrained = 1
};

const semi_ids_t ServiceRequestIDs = {
		.dialog_id_constrained = 0,
		.seq_id = 0x01,
		.seq_id_constrained = 1
};

const semi_ids_t ServiceResponseIDs = {
		.dialog_id_constrained = 0,
		.seq_id = 0x02,
		.seq_id_constrained = 1
};

const semi_ids_t DataRequestIDs = {
		.dialog_id_constrained = 0,
		.seq_id = 0x03,
		.seq_id_constrained = 1
};

const semi_ids_t AdvisorySituationDataDistributionIDs = {
		.dialog_id = 0x9D,
		.dialog_id_constrained = 1,
		.seq_id = 0x05,
		.seq_id_constrained = 1
};

const semi_ids_t DataAcceptanceIDs = {
		.dialog_id_constrained = 0,
		.seq_id = 0x06,
		.seq_id_constrained = 1
};

const semi_ids_t DataReceiptIDs = {
		.dialog_id_constrained = 0,
		.seq_id = 0x07,
		.seq_id_constrained = 1
};

const semi_ids_t AdvisorySituationBundleIDs = {
        .dialog_id = 0,
        .dialog_id_constrained = 0,
        .seq_id = 0,
        .seq_id_constrained = 0
};

enum asn1Type
{
	ASN1TYPE_ADVISORY_SITUATION_DATA = 0,
	ASN1TYPE_SERVICE_REQUEST = 1,
	ASN1TYPE_SERVICE_RESPONSE = 2,
	ASN1TYPE_DATA_REQUEST = 3,
	ASN1TYPE_ADVISORY_SITUATION_DATA_DISTRIBUTION = 4,
	ASN1TYPE_DATA_ACCEPTANCE = 5,
	ASN1TYPE_DATA_RECEIPT = 6,
	ASN1TYPE_ADVISORY_SITUATION_BUNDLE = 7
};


void left_shift_buffer(uint8_t *buf, size_t buf_size)
{
	int carry_in = 0;

	for (int i = buf_size-1; i >= 0; --i) {
		uint8_t carry_out = !!(buf[i] & 0x80);
		buf[i] <<= 1;
		buf[i] &= carry_out;
		carry_in = carry_out;
	}
}

int try_extract_semi_ids(const struct asn_codec_ctx_s *opt_codec_ctx, uint8_t *buf, size_t buf_size, SemiDialogID_t **dialog_id, SemiSequenceID_t **seq_id)
{
	void *dialog_id_proxy = dialog_id;
	asn_dec_rval_t dialog_id_rval = asn_decode(opt_codec_ctx, ATS_UNALIGNED_CANONICAL_PER, &asn_DEF_SemiDialogID, dialog_id_proxy, buf, buf_size);
	dialog_id = dialog_id_proxy;
	if (dialog_id_rval.code == RC_OK) {
		uint8_t *shift_buf = malloc(buf_size);
		memcpy(shift_buf, buf, buf_size);
		size_t offset = dialog_id_rval.consumed;
		for(int i = 0; i < offset; ++i) {
			left_shift_buffer(shift_buf, buf_size-i);
		}
		void *seq_id_proxy = seq_id;
		asn_dec_rval_t seq_id_rval = asn_decode(opt_codec_ctx, ATS_UNALIGNED_CANONICAL_PER, &asn_DEF_SemiSequenceID, seq_id_proxy, shift_buf, buf_size-offset);
		seq_id = seq_id_proxy;
		free(shift_buf);
		if (seq_id_rval.code == RC_OK) {
			return 0;
		}

		free(*dialog_id);
	}

	return -1;
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
    case ASN1TYPE_ADVISORY_SITUATION_BUNDLE:
        return &asn_DEF_AdvisorySituationBundle;
	default:
		return NULL;
	}
}

const semi_ids_t *get_expected_type_ids(enum asn1Type type) {
	switch (type)
	{
	case ASN1TYPE_ADVISORY_SITUATION_DATA:
		return &AdvisorySituationDataIDs;
	case ASN1TYPE_SERVICE_REQUEST:
		return &ServiceRequestIDs;
	case ASN1TYPE_SERVICE_RESPONSE:
		return &ServiceResponseIDs;
	case ASN1TYPE_DATA_REQUEST:
		return &DataRequestIDs;
	case ASN1TYPE_ADVISORY_SITUATION_DATA_DISTRIBUTION:
		return &AdvisorySituationDataDistributionIDs;
	case ASN1TYPE_DATA_ACCEPTANCE:
		return &DataAcceptanceIDs;
	case ASN1TYPE_DATA_RECEIPT:
		return &DataReceiptIDs;
  case ASN1TYPE_ADVISORY_SITUATION_BUNDLE:
        return &AdvisorySituationBundleIDs;
	default:
		return NULL;
	}
}

int ids_are_expected(const semi_ids_t *expected_ids, SemiDialogID_t *dialog_id, SemiSequenceID_t *seq_id)
{
	return (!expected_ids->dialog_id_constrained
				|| expected_ids->dialog_id == *dialog_id)
	    && (!expected_ids->seq_id_constrained
	    			|| expected_ids->seq_id == *seq_id);
}

int get_type_ids(enum asn1Type type, void* data, SemiDialogID_t *dialog_id, SemiSequenceID_t *seq_id)
{
	switch (type)
	{
	case ASN1TYPE_ADVISORY_SITUATION_DATA:
		*dialog_id = ((AdvisorySituationData_t*)data)->dialogID;
		*seq_id = ((AdvisorySituationData_t*)data)->seqID;
		return 1;
	case ASN1TYPE_SERVICE_REQUEST:
		*dialog_id = ((ServiceRequest_t*)data)->dialogID;
		*seq_id = ((ServiceRequest_t*)data)->seqID;
		return 1;
	case ASN1TYPE_SERVICE_RESPONSE:
		*dialog_id = ((ServiceResponse_t*)data)->dialogID;
		*seq_id = ((ServiceResponse_t*)data)->seqID;
		return 1;
	case ASN1TYPE_DATA_REQUEST:
		*dialog_id = ((DataRequest_t*)data)->dialogID;
		*seq_id = ((DataRequest_t*)data)->seqID;
		return 1;
	case ASN1TYPE_ADVISORY_SITUATION_DATA_DISTRIBUTION:
		*dialog_id = ((AdvisorySituationDataDistribution_t*)data)->dialogID;
		*seq_id = ((AdvisorySituationDataDistribution_t*)data)->seqID;
		return 1;
	case ASN1TYPE_DATA_ACCEPTANCE:
		*dialog_id = ((DataAcceptance_t*)data)->dialogID;
		*seq_id = ((DataAcceptance_t*)data)->seqID;
		return 1;
	case ASN1TYPE_DATA_RECEIPT:
		*dialog_id = ((DataReceipt_t*)data)->dialogID;
		*seq_id = ((DataReceipt_t*)data)->seqID;
		return 1;
  case ASN1TYPE_ADVISORY_SITUATION_BUNDLE:
        *dialog_id = -1;
        *seq_id = -1;
        return 1;
	default:
		return 0;
	}
}

int type_ids_valid(enum asn1Type type, SemiDialogID_t* dialog_id, SemiSequenceID_t* seq_id)
{
	const semi_ids_t *expected_ids = get_expected_type_ids(type);

	if(expected_ids == NULL) {
		return 0;
	} else {
		return ids_are_expected(expected_ids, dialog_id, seq_id);
	}
}

int decoded_ids_valid(enum asn1Type type, void *data)
{
	SemiDialogID_t dialog_id;
	SemiSequenceID_t seq_id;

	if (get_type_ids(type, data, &dialog_id, &seq_id)) {
		return type_ids_valid(type, &dialog_id, &seq_id);
	} else {
		return 0;
	}
}

int xer_to_per_c(const struct asn_codec_ctx_s *opt_codec_ctx,
                  const struct asn_TYPE_descriptor_s *type_descriptor,
                  const void *input_buffer,
                  size_t input_size,
                  void **output_buffer_ptr,
                  size_t *output_size_ptr,
                  asn_dec_rval_t *dec_rval,
                  asn_enc_rval_t *enc_rval,
				  void **intermediate_ptr)
{
	void *local_intermediate;

	void **local_intermediate_ptr;

	if (intermediate_ptr == NULL) {
		local_intermediate_ptr = &local_intermediate;
	} else {
		local_intermediate_ptr = intermediate_ptr;
	}
	*local_intermediate_ptr = NULL;

    *output_buffer_ptr = NULL;

    *dec_rval = asn_decode(opt_codec_ctx, ATS_CANONICAL_XER, type_descriptor, local_intermediate_ptr, input_buffer, input_size);
    if (dec_rval->code != RC_OK) {
    		return 0;
    }

    asn_encode_to_new_buffer_result_t enc_result = asn_encode_to_new_buffer(opt_codec_ctx, ATS_UNALIGNED_CANONICAL_PER, type_descriptor, *local_intermediate_ptr);

    *output_buffer_ptr = enc_result.buffer;
    *enc_rval = enc_result.result;
    *output_size_ptr = enc_rval->encoded;

    if (intermediate_ptr == NULL) {
    		free(local_intermediate);
    }

    if (enc_rval->encoded == -1) {
    		return 0;
    } else {
    		return 1;
    }
}

int per_to_xer_c(const struct asn_codec_ctx_s *opt_codec_ctx,
                  const struct asn_TYPE_descriptor_s *type_descriptor,
                  const void *input_buffer,
                  size_t input_size,
                  void **output_buffer_ptr,
                  size_t *output_size_ptr,
                  asn_dec_rval_t *dec_rval,
                  asn_enc_rval_t *enc_rval,
				  void **intermediate_ptr)
{
	void *local_intermediate;

	void **local_intermediate_ptr;

	if (intermediate_ptr == NULL) {
		local_intermediate_ptr = &local_intermediate;
	} else {
		local_intermediate_ptr = intermediate_ptr;
	}
	*local_intermediate_ptr = NULL;

	*output_buffer_ptr = NULL;

	*dec_rval = asn_decode(opt_codec_ctx, ATS_UNALIGNED_CANONICAL_PER, type_descriptor, local_intermediate_ptr, input_buffer, input_size);
	if (dec_rval->code != RC_OK) {
		return 0;
	}

	asn_encode_to_new_buffer_result_t enc_result = asn_encode_to_new_buffer(opt_codec_ctx, ATS_CANONICAL_XER, type_descriptor, *local_intermediate_ptr);

	*output_buffer_ptr = enc_result.buffer;
	*enc_rval = enc_result.result;

	if (intermediate_ptr == NULL) {
		free(local_intermediate);
	}

	if (enc_rval->encoded == -1) {
		return 0;
	} else {
		return 1;
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
    		void *intermediate;

		if (xer_to_per_c(&dummy_context, type_descriptor, xer_c, xer_c_size, &per_c, &per_c_size, &dec_rval, &enc_rval, &intermediate)) {
			// If successful, check that the ids of the decoded type are correct
			if (decoded_ids_valid(type, intermediate)) {
				// If valid, copy the PER c byte array into the java byte array
				per = (*env)->NewByteArray(env, per_c_size);
				(*env)->SetByteArrayRegion(env, per, 0, per_c_size, per_c);
			}
			free(intermediate);
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

		void *intermediate;

		void *xer_c_proxy = xer_c;

		if (per_to_xer_c(&dummy_context, type_descriptor, per_c, per_c_size, &xer_c_proxy, &xer_c_size, &dec_rval, &enc_rval, &intermediate)) {
			xer_c = xer_c_proxy;
			// If successful, check that the ids of the decoded type are correct
			if (decoded_ids_valid(type, intermediate)) {
				// If valid, copy the PER c byte array into the java byte array

				xer = (*env)->NewStringUTF(env, xer_c);
			}

			free(intermediate);
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

JNIEXPORT jint JNICALL Java_gov_dot_its_jpo_sdcsdw_asn1_perxercodec_Native_getAdvisorySituationBundleType(JNIEnv * env, jclass myClass)
{
    return ASN1TYPE_ADVISORY_SITUATION_BUNDLE;
}
