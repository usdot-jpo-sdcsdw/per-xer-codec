package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import java.util.Arrays;

/** PER data which is encoded directly as a byte-string
 * 
 * @author amm30955
 *
 */
public class RawPerData implements PerData<byte[]>
{
    /** Create a PER data object from a byte string 
     * 
     * @param rawPerData Byte string containing PER data
     */
    public RawPerData(byte[] rawPerData)
    {
        if (rawPerData == null) {
            throw new IllegalArgumentException("rawPerData cannot be null");
        }
        
        this.perData = rawPerData;
    }
    
    /**
     * Formatter for this type
     */
    public static final PerDataFormatter<byte[], RawPerData> formatter = RawPerData::new;
    /**
     * Unformatter for this type
     */
    public static final PerDataUnformatter<byte[], RawPerData> unformatter = RawPerData::new;
    
    @Override
    public byte[] getPerData()
    {
        return perData;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(perData);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (obj instanceof PerData<?>) {
            return ((PerData<?>) obj).getPerData().equals(perData);
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return perData.toString();
    }

    /** Byte string containing PER data
     * 
     */
    private final byte[] perData;

	@Override
	public byte[] getFormattedPerData() {
		return perData;
	}
}
