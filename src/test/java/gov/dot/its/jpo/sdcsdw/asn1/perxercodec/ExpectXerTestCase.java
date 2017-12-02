package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerData;

public interface ExpectXerTestCase<T> extends TypedTestCase
{
	T getExpectedData();
	
	XerData getExpectedXerData();
}
