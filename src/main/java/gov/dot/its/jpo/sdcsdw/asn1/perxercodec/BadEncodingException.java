package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Exception thrown when a XerDataBuilder or PerDataBuilder instance could not build the requested encoding of data
 * 
 * This almost always indicates an error in a user-defined XerDataBuilder or PerDataBuilder
 * 
 * @author andrew
 *
 */
public class BadEncodingException extends CodecException
{
	/**
	 * Create a bad encoding exception with a message and an underlying exception
	 * @param msg Message for this exception
	 * @param cause Underlying exception
	 */
	public BadEncodingException(String msg, Exception cause)
	{
		super(msg, cause);
	}

	/**
	 * Create a bad encoding exception with a message
	 * @param msg Message for this exception
	 */
	public BadEncodingException(String msg)
	{
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8525487737978306837L;
}
