package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

/**
 * Functional interface for building XerData objects
 * @author andrew
 *
 * @param <T> Input type
 * @param <XerT> Subclass of XerData this function builds
 */
public interface XerDataUnformatter<T, XerT extends XerData<T>>
{
	/**
	 * Produce a subobject of XerData from input
	 * @param t Input data
	 * @return A subobject of XerData containing the input data
	 * @throws UnformattingFailedException If the building failed
	 */
	XerT unformatXerData(T t) throws UnformattingFailedException;
}
