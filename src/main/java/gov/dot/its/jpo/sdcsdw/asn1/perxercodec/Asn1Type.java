package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Opaque type for selecting which type to encode or decode as 
 */
public class Asn1Type
{
    /** Construct an ASN.1 type from the native enum value 
     * 
     * @param cInt Native enum value for the type
     * @param name Name users can use to refer to this type
     */
    Asn1Type(String name, int cInt)
    {
        this.cInt = cInt;
        this.name = name;
    }
    
    /** Get the name used to reference this type
     * @return The name used to reference this type
     */
    public String getName()
    {
    	return name;
    }
    
    @Override
	public
    int hashCode()
    {
    	return Integer.hashCode(cInt);
    }
    
    @Override
	public
    boolean equals(Object obj)
    {
    	return (obj instanceof Asn1Type) ? ((Asn1Type)obj).cInt == cInt : false; 
    }
    
    /**
     * Native enum value of this type
     */
    final int cInt;
    
    /**
     * The name users can use to refer to this type
     */
    final String name;
}