package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Opaque type for selecting which type to encode or decode as 
 */
public class Asn1Type
{
    /** Construct an ASN.1 type from the native enum value 
     * 
     * @param cInt Native enum value for the type
     */
    Asn1Type(int cInt)
    {
        this.cInt = cInt;
    }
    
    /** Native enum value of this type
     * 
     */
    final int cInt;
}