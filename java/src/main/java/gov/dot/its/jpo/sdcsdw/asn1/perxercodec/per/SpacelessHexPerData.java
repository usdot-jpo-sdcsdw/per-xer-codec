package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import java.util.Arrays;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

/** PER data which is encoded as a string of hexadecimal digits 
 * 
 * <p>
 * 
 * The unformatter accepts the same data as HexPerData, however, the formatter
 * will output data without spaces between octets
 * 
 * @author amm30955
 *
 */
public class SpacelessHexPerData implements PerData<String>
{   
    /** Create a PER data object from a string containing hexadecmial digits
     * 
     * <p>
     * 
     * Each character in hexPerData should be 0-9, a-f, A-F, x, or whitespace
     * If whitespace is present, digits should be grouped into bytes, i.e. two digits
     * A leading 0x or 0X is optional, but permitted, either as a single prefix, or a per-byte prefix
     * 
     * 
     * @param hexPerData PER data formatted as a hexadecimal string
     * @throws UnformattingFailedException If the string could not be interpreted 
     */
    public SpacelessHexPerData(String hexPerData) throws UnformattingFailedException
    {
        if (hexPerData == null) {
            throw new IllegalArgumentException("hexPerData cannot be null");
        }
        
        this.hexPerData = hexPerData;
        
        final String lowerSpacelessHexPerData = hexPerData.toLowerCase();
        
        final String prefix = "0x";
        
        final String oneOrMoreSpacesRegex = "\\s+";
        
        final String[] sections = lowerSpacelessHexPerData.split(oneOrMoreSpacesRegex);
        
        if (hexPerData.length() == 0) {
            // Empty string, no data
            perData = new byte[0];
        } else if (sections.length == 1) {
            // We have one big string of bytes
            final String allBytesString = sections[0];
            
            // There has to be an even number of characters, even if we have the 0x prefix
            if (allBytesString.length() % 2 != 0) {
                throw new UnformattingFailedException("Received an odd number of hex digits, must be provided as a complete set of bytes");
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
                    throw new UnformattingFailedException("0x and non-0x bytes mis-matched");
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
    
    /**
     * Build a PER data object from a byte string
     * @param perData Byte string containing PER data
     */
    public SpacelessHexPerData(byte[] perData)
    {
        if (perData == null) {
            throw new IllegalArgumentException("perData cannot be null");
        }
        
        this.perData = perData;
        StringBuilder hexPerData = new StringBuilder();
        for(byte b : perData) {
            hexPerData.append(String.format("%02X", b));
        }
        this.hexPerData = hexPerData.toString();
    }
    
    /**
     * Formatter for this type
     */
    public static final PerDataFormatter<String, SpacelessHexPerData> formatter = SpacelessHexPerData::new;
    /**
     * Unformatter for this type
     */
    public static final PerDataUnformatter<String, SpacelessHexPerData> unformatter = SpacelessHexPerData::new;
    
    @Override
    public byte[] getPerData()
    {
        return perData;
    }
    
    
    @Override
    public String getFormattedPerData()
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
        if (obj instanceof PerData<?>) {
            return Arrays.equals(((PerData<?>) obj).getPerData(), perData);
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
