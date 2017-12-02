package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

/** Interface for Representing XER data as a UTF8 string as well as an "External" type.
 * 
 * @author amm30955
 * @param <T> The "External" type
 */
public interface XerData<T>
{
    /**
     * Get the XER data of this object as a UTF8 string
     * 
     * @return A UTF8 string containing the XER data for this object
     */
    String getXerData();
    
    /**
     * Get the XER data of this object as the "External" type
     * @return The XER data of this object as the "External" type
     */
    T getFormattedXerData();
}
