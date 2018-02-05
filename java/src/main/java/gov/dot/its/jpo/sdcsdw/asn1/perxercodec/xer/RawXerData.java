package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

/**
 * XER data which is encoded directly as the UTF8 XML string
 * 
 * @author andrew
 *
 */
public class RawXerData implements XerData<String>
{
	/**
	 * Create a XER data object from a string containing XML
	 * 
	 * @param xerData String containing XML
	 */
    public RawXerData(String xerData)
    {
        if (xerData == null) {
            throw new IllegalArgumentException("xerData cannot be null");
        } else if (xerData == "") {
            throw new IllegalArgumentException("xerData cannot be empty");
        }
        
        this.xerData = xerData;
    }
    
    /**
     * Formatter for this type
     */
    public static final XerDataFormatter<String, RawXerData> formatter = RawXerData::new;
    /**
     * Unformatter for this type
     */
    public static final XerDataUnformatter<String, RawXerData> unformatter = RawXerData::new;

    @Override
    public String getXerData()
    {
        return xerData;
    }
    
    @Override
    public String getFormattedXerData()
    {
        return xerData;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((xerData == null) ? 0 : xerData.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof XerData<?>) {
            return ((XerData<?>) obj).getXerData().equals(xerData);
        }
        
        return false;
    }
    
    @Override
    public String toString()
    {
        return xerData;
    }

    /**
     * String containing XML data
     */
    private final String xerData;
}
