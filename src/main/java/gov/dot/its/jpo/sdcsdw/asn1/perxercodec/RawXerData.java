package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

public class RawXerData implements XerData
{
    public RawXerData(String xerData)
    {
        this.xerData = xerData;
    }

    @Override
    public String getXerData()
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
        if (obj instanceof XerData) {
            return ((XerData) obj).getXerData().equals(xerData);
        }
        
        return false;
    }
    
    @Override
    public String toString()
    {
        return xerData;
    }

    private final String xerData;
}
