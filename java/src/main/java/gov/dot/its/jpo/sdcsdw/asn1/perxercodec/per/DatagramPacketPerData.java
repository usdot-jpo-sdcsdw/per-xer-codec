package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import java.net.DatagramPacket;
import java.util.Arrays;

/** PER data contained in a datagram
 * 
 * @author andrew
 *
 */
public class DatagramPacketPerData implements PerData<DatagramPacket>
{
	/** Create a PER object from the bytes in a datagram
	 * 
	 * @param perPacket Datagram containing PER bytes
	 */
	public DatagramPacketPerData(DatagramPacket perPacket)
	{
	    if (perPacket == null) {
	        throw new IllegalArgumentException("perPacket cannot be null");
	    }
	    
		this.perPacket = perPacket;
	}
	
	/** Create a PER object from a byte string, and wrap them in a datagram 
	 * 
	 * @param perData Byte string containing PER
	 */
	public DatagramPacketPerData(byte[] perData)
	{
	    if (perData == null) {
	        throw new IllegalArgumentException("perData cannot be null");
	    }
	    
		perPacket = new DatagramPacket(perData, perData.length);
	}
	
    /**
     * Formatter for this type
     */
    public static final PerDataFormatter<DatagramPacket, DatagramPacketPerData> formatter = DatagramPacketPerData::new;
    /**
     * Unformatter for this type
     */
    public static final PerDataUnformatter<DatagramPacket, DatagramPacketPerData> unformatter = DatagramPacketPerData::new;

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((perPacket == null) ? 0 : perPacket.hashCode());
        return result;
    }
	
	@Override
	public byte[] getPerData()
	{
		if (perPacket.getOffset() == 0 && perPacket.getData().length == perPacket.getLength()) {
			return perPacket.getData();
		} else {
			return Arrays.copyOfRange(perPacket.getData(), perPacket.getOffset(), perPacket.getOffset() + perPacket.getLength());
		}
	}

	@Override
	public DatagramPacket getFormattedPerData()
	{
		return perPacket;
	}
	
	@Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof PerData<?>) {
            return Arrays.equals(((PerData<?>) obj).getPerData(), getPerData());
        }
        
        return false;
    }
	
	@Override
	public String toString()
	{
	    return Arrays.toString(getPerData());
	}
	
	/** Datagram containing PER bytes
	 * 
	 */
	private final DatagramPacket perPacket;
}
