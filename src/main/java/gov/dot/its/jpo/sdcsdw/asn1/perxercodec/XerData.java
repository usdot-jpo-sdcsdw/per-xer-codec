package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/** Interface for extracting XER data as a UTF8 string
 * 
 * @author amm30955
 */
public interface XerData
{
    /** Extract the XER data of this object as a UTF8 string
     * 
     * @return A UTF8 string containing the XER data for this object
     */
    public String getXerData();
}
