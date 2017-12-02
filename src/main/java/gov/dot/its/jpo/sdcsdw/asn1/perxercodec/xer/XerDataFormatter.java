package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;

/** Functional interface for functions which can build XerData from a XML string, or fail 
 * 
 * @author andrew
 *
 * @param <T> Instance of XerData this builder will build
 * @param <XerT> Subtype of XerData to build
 */
public interface XerDataFormatter<T, XerT extends XerData<T>> {
	/** Build a XerData instance from a string
	 * 
	 * @param xer XML string containing XER data
	 * @return The XerData instance built
	 * @throws FormattingFailedException If the instance could not create the desired
	 * 	representation, this usually indicates an error in a user-defined implementation of XerData
	 */
	XerT formatXerData(String xer) throws FormattingFailedException;
}
