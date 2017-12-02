package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

/** PER data which is encoded as a base64 string
 * 
 * @author amm30955
 *
 */
public class Base64PerData implements PerData<String>
{
    /** Create a PER data object from a base64 string
     * 
     * @param base64PerData PER data formatted as a base64 string
     * @throws UnformattingFailedException If the string could not be interpreted 
     */
    public Base64PerData(String base64PerData) throws UnformattingFailedException
    {
        this.base64PerData = base64PerData;
        
        try {
            this.perData = DatatypeConverter.parseBase64Binary(base64PerData);
        } catch (IllegalArgumentException ex) {
            throw new UnformattingFailedException("String did not contain valid base-64 data", ex);
        }
    }
    
    /** Create a PER data object from a byte string and convert it to base 64
     * 
     * @param perData Byte string containing PER data
     */
    public Base64PerData(byte[] perData)
    {
        this.perData = perData;
        this.base64PerData = DatatypeConverter.printBase64Binary(perData);
    }

    /**
     * Formatter for this type
     */
    public static final PerDataFormatter<String, Base64PerData> formatter = Base64PerData::new;
    /**
     * Unformatter for this type
     */
    public static final PerDataUnformatter<String, Base64PerData> unformatter = Base64PerData::new;
    
    @Override
    public byte[] getPerData()
    {
        return perData;
    }
    
    @Override
    public String getFormattedPerData()
    {
        return base64PerData;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((base64PerData == null) ? 0 : base64PerData.hashCode());
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
        if (obj instanceof PerData<?>) {
            return ((PerData<?>) obj).getPerData().equals(perData);
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return base64PerData.toString();
    }

    /** String containing PER data as base64
     * 
     */
    private final String base64PerData;
    
    /** Byte string containing PER data
     * 
     */
    private final byte[] perData;
}
