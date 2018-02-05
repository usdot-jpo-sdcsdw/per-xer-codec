package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import java.nio.ByteBuffer;

/** PER data contained in a byte buffer
 * 
 * @author andrew
 *
 */
public class ByteBufferPerData implements PerData<ByteBuffer>
{
	/** Create a PER object from a Byte buffer
	 * 
	 * @param perBuffer Buffer containing PER bytes 
	 */
	public ByteBufferPerData(ByteBuffer perBuffer)
	{
	    if (perBuffer == null) {
	        throw new IllegalArgumentException("perBuffer cannot be null");
	    }
	    
		this.perBuffer = perBuffer;
	}
	
	/** Create a PER object from a byte string, and wrap it in a ByteBuffer
	 * 
	 * @param perData Bytes containing PER data
	 */
	public ByteBufferPerData(byte[] perData)
	{
	    if (perData == null) {
	        throw new IllegalArgumentException("perData cannot be null");
	    }
	
	    
		this.perBuffer = ByteBuffer.wrap(perData);
	}
	
    /**
     * Formatter for this type
     */
    public static final PerDataFormatter<ByteBuffer, ByteBufferPerData> formatter = ByteBufferPerData::new;
    /**
     * Unformatter for this type
     */
    public static final PerDataUnformatter<ByteBuffer, ByteBufferPerData> unformatter = ByteBufferPerData::new;

	@Override
	public byte[] getPerData() {
		byte[] perData = new byte[perBuffer.capacity()];
		perBuffer.get(perData);
		return perData;
	}

	@Override
	public ByteBuffer getFormattedPerData() {
		return perBuffer;
	}
	
	/** Buffer containing PER data
	 * 
	 */
	private final ByteBuffer perBuffer;
}
