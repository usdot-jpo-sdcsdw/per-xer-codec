package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception;

/** Superclass of exceptions thrown by the codec
 * 
 * @author andrew
 *
 */
public class CodecException extends Exception
{
	/**
	 * Create a codec exception with a message and an underlying exception
	 * @param msg Message for this exception
	 * @param cause The underlying exception
	 */
	public CodecException(String msg, Exception cause)
	{
		super(msg, cause);
	}
	
	/**
	 * Create a codec exception with a message
	 * @param msg Message for this exception
	 */
	public CodecException(String msg)
	{
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 381786070653122780L;
}
