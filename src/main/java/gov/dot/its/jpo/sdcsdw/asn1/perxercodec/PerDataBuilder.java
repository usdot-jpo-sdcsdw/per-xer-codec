package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Functional interface for functions which can build PerData from raw bytes, or fail 
 * 
 * @author andrew
 *
 * @param <T> Instance of PerData this builder will build
 */
public interface PerDataBuilder<T extends PerData> {
	/** Build a PerData instance from bytes
	 * 
	 * @param per Raw bytes containing PER data
	 * @return The PerData instance built
	 * @throws BadEncodingException If the instance could not create the desired
	 *  representation, this usually indicates an error in a user-defined implementation of PerData
	 */
	T buildPerData(byte[] per) throws BadEncodingException;
}
