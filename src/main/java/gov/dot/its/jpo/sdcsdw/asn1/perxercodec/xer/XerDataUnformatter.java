package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

public interface XerDataUnformatter<T, XerT extends XerData>
{
	XerT unformatXerData(T t) throws UnformattingFailedException;
}
