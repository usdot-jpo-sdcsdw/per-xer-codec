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
		this.perPacket = perPacket;
	}
	
	/** Create a PER object from a byte string, and wrap them in a datagram 
	 * 
	 * @param perData Byte string containing PER
	 */
	public DatagramPacketPerData(byte[] perData)
	{
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
	public byte[] getPerData()
	{
		if (perPacket.getOffset() == 0 && perPacket.getData().length == perPacket.getLength()) {
			return perPacket.getData();
		} else {
			return Arrays.copyOfRange(perPacket.getData(), perPacket.getOffset(), perPacket.getLength());
		}
	}

	@Override
	public DatagramPacket getFormattedPerData()
	{
		return perPacket;
	}
	
	/** Datagram containing PER bytes
	 * 
	 */
	private final DatagramPacket perPacket;
}
