package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;

/** Functional interface for functions which can build specific subclasses of PerData from raw bytes.
 * 
 * @author andrew
 *
 * @param <T> The type the PerData objects built must be able to be represented as
 * @param <PerT> Subtype of PerData to build 
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
