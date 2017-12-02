package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Exception thrown when a XerData or PerData instance cannot interpret encoded data
 * 
 * @author andrew
 *
 */
public class BadDecodingException extends CodecException
{
	/**
	 * Create a bad decoding exception with a message and an underlying exception
	 * @param msg Message for this exception
	 * @param cause Underlying exception
	 */
	public BadDecodingException(String msg, Exception cause)
	{
		super(msg, cause);
	}

	/**
	 * Create a bad decoding exception with a message
	 * @param msg Message for this exception
	 */
	public BadDecodingException(String msg)
	{
		super(msg);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8211494033203210008L;
}
