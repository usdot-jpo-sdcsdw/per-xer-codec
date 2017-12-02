package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerData;

public interface ExpectPerTestCase<T> extends TypedTestCase
{
	T getExpectedData();
	
	PerData getExpectedPerData();
}
