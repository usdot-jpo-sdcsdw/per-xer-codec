package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception;

/** Exception thrown when a XerData or PerData instance cannot interpret formatted data
 * 
 * @author andrew
 *
 */
public class UnformattingFailedException extends CodecException
{
	/**
	 * Create a bad decoding exception with a message and an underlying exception
	 * @param msg Message for this exception
	 * @param cause Underlying exception
	 */
	public UnformattingFailedException(String msg, Exception cause)
	{
		super(msg, cause);
	}

	/**
	 * Create a bad decoding exception with a message
	 * @param msg Message for this exception
	 */
	public UnformattingFailedException(String msg)
	{
		super(msg);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8211494033203210008L;
}
