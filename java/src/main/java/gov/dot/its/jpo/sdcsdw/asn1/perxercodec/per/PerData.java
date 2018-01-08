package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

/** 
 * Interface for representing PER data as a byte string as well as an "External" type.
 * 
 * <p>
 * 
 * Classes implementing this interface should override equals() to return true if they represent the same binary string, i.e.
 *  A) The other object also implements PerData
 *  B) Calling equals() on the two values of getPerData() returns true
 * 
 * @author amm30955
 * @param <T> The "External" type 
 *
 */
public interface PerData<T>
{
    /**
     * Get the PER data of this object as a byte-string
     * @return A byte-string containing the PER data of this object
     */
    byte[] getPerData();
    
    /**
     * Get the PER data of this object as the "External" type 
     * @return The PER data of this object as the "External" type
     */
    T getFormattedPerData();
}
