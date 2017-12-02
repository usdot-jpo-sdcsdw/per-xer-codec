package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.util.Arrays;

/** PER data which is encoded as a string of hexadecimal digits 
 * 
 * @author amm30955
 *
 */
public class HexPerData implements PerData
{
    /** Create a PER data object from a string containing hexadecmial digits
     * 
     * Each character in hexPerData should be 0-9, a-f, A-F, x, or whitespace
     * If whitespace is present, digits should be grouped into bytes, i.e. two digits
     * A leading 0x or 0X is optional, but permitted, either as a single prefix, or a per-byte prefix
     * 
     * 
     * @param hexPerData PER data formatted as a hexadecimal string
     * @throws NumberFormatException If the string could not be interpreted
     */
    public HexPerData(String hexPerData) throws NumberFormatException
    {
        this.hexPerData = hexPerData;
        
        final String lowerHexPerData = hexPerData.toLowerCase();
        
        final String prefix = "0x";
        
        final String oneOrMoreSpacesRegex = "\\s+";
        
        final String[] sections = lowerHexPerData.split(oneOrMoreSpacesRegex);
        
        if (sections.length == 0) {
            // Empty string, no data
            perData = new byte[0];
        } else if (sections.length == 1) {
            // We have one big string of bytes
            final String allBytesString = sections[0];
            
            // There has to be an even number of characters, even if we have the 0x prefix
            if (allBytesString.length() % 2 != 0) {
                throw new NumberFormatException("Received an odd number of hex digits, must be provided as a complete set of bytes");
            }
            
            // Strip off the prefix, if it's there
            final String cleanedAllBytesString;
            
            if (allBytesString.startsWith(prefix)) {
                cleanedAllBytesString = allBytesString.substring(2);
            } else {
                cleanedAllBytesString = allBytesString;
            }
            
            // Find out how many bytes we are going to decode
            final int byteCount = cleanedAllBytesString.length() / 2;
            
            perData = new byte[byteCount];
            
            // Parse each byte as an unsigned 
            for(int byteIndex = 0; byteIndex < byteCount; ++byteIndex) {
                String byteChars = cleanedAllBytesString.substring(2 * byteIndex, 2 * byteIndex + 2);
                perData[byteIndex] = (byte) Integer.parseInt(byteChars, 16); 
            }
            
            // All good
        } else {
            // We have multiple bytes
            
            // Assume that all of the sections have or don't have the prefix
            final boolean prefixPresent = sections[0].startsWith(prefix);
            
            final int byteCount = sections.length;
            
            perData = new byte[byteCount];
            
            int byteIndex = 0;
            
            // For each assumption
            for (String byteString : sections) {
                // Remove the prefix, if its there
                final String cleanedByteString;
                
                // Enforce the all-or-nothing for prefixes
                if ((prefixPresent && byteString.length() != 4) || (!prefixPresent && byteString.length() != 2)) {
                    throw new NumberFormatException("0x and non-0x bytes mis-matched");
                }
                
                if (prefixPresent) {
                    cleanedByteString = byteString.substring(2);
                } else {
                    cleanedByteString = byteString;
                }
                
                // Parse the remaining characters
                perData[byteIndex++] = (byte) Integer.parseInt(cleanedByteString, 16);
            }
        }
    }
    
    public HexPerData(byte[] perData)
    {
        this.perData = perData;
        StringBuilder hexPerData = new StringBuilder();
        for(byte b : perData) {
            hexPerData.append(String.format("%02X ", b));
        }
        this.hexPerData = hexPerData.toString();
    }
    
    @Override
    public byte[] getPerData()
    {
        return perData;
    }
    
    /** Get the original hex data used to create this object
     * 
     * @return The original hex data used to create this object
     */
    public String getHexPerData()
    {
        return hexPerData;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((hexPerData == null) ? 0 : hexPerData.hashCode());
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
        return hexPerData;
    }
    
    /** String containing PER data as hexadecimal digits
     *  
     */
    private final String hexPerData;


    /** Byte string containing PER data
     * 
     */
    private final byte[] perData;    
}
