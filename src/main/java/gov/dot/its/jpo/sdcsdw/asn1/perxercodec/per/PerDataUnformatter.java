package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

/**
 * Functional interface for types which can build PER data from a given type of data
 * @author andrew
 *
 * @param <T> Input data type
 */
public interface PerDataUnformatter<T, PerT extends PerData>
{
	/**
	 * Encode input data as PER data
	 * @param t Input data
	 * @return The input data encoded as PER data
	 * @throws UnformattingFailedException TODO
	 */
	PerT unformatPerData(T t) throws UnformattingFailedException;
}
