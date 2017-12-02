package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;

/** Functional interface for functions which can build PerData from raw bytes, or fail 
 * 
 * @author andrew
 *
 * @param <T> Instance of PerData this builder will build
 */
public interface PerDataFormatter<T, PerT extends PerData<T>> {
	/** Build a PerData instance from bytes
	 * 
	 * @param per Raw bytes containing PER data
	 * @return The PerData instance built
	 * @throws FormattingFailedException TODO
	 */
	PerT formatPerData(byte[] per) throws FormattingFailedException;
}
