package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

/** PER data which is encoded as a base64 string
 * 
 * @author amm30955
 *
 */
public class Base64PerData implements PerData
{
    /** Create a PER data object from a base64 string
     * 
     * @param base64PerData PER data formatted as a base64 string
     * @throws BadEncodingException If the string could not be interpreted
     */
    public Base64PerData(String base64PerData) throws BadEncodingException
    {
        this.base64PerData = base64PerData;
        
        try {
            this.perData = DatatypeConverter.parseBase64Binary(base64PerData);
        } catch (IllegalArgumentException ex) {
            throw new BadEncodingException("Could not decode base64 data", ex);
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

    @Override
    public byte[] getPerData()
    {
        return perData;
    }
    
    /** Get the original base64 string used to create this object
     * 
     * @return The original base64 string used to create this object
     */
    public String getBase64PerData()
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
        if (obj instanceof PerData) {
            return ((PerData) obj).getPerData().equals(perData);
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
