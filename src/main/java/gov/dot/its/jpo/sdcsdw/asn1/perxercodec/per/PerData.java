package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

/** 
 * Interface for extracting PER data as a byte string.
 * 
 * </p>
 * 
 * Classes implementing this interface should override equals() to return true if they represent the same binary string, i.e.
 *  A) The other object also implements PerData
 *  B) Calling equals() on the two values of getPerData() returns true
 * 
 * @author amm30955
 *
 */
public interface PerData<T>
{
    /** Extract the PER data of this object as a byte-string
     * 
     * @return A byte-string containing the PER data of this object
     */
    byte[] getPerData();
    
    T getFormattedPerData();
}
