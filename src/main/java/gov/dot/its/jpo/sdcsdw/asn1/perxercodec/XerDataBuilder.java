package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Functional interface for functions which can build XerData from raw bytes, or fail 
 * 
 * @author andrew
 *
 * @param <T> Instance of XerData this builder will build
 */
public interface XerDataBuilder<T extends XerData> {
	/** Build a XerData instance from a string
	 * 
	 * @param xer XML string containing XER data
	 * @return The XerData instance built
	 * @throws BadEncodingException If the instance could not create the desired
	 * 	representation, this usually indicates an error in a user-defined implementation of XerData
	 */
	T buildXerData(String xer) throws BadEncodingException;
}
