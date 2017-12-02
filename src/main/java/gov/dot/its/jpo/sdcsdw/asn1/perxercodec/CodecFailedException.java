package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Exception thrown when the codec cannot convert data
 * 
 * @author andrew
 *
 */
public class CodecFailedException extends CodecException
{
	/**
	 * Create a new CodecFailedException from a message
	 * @param msg Message for this exception
	 */
	public CodecFailedException(String msg)
	{
		super(msg);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4991686932174858808L;	
}
