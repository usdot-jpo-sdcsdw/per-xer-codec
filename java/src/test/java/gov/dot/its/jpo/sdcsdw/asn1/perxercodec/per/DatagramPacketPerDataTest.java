package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import static org.junit.jupiter.api.Assertions.*;

import java.net.DatagramPacket;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DatagramPacketPerDataTest
{
    private static byte[] expectedBytes = new byte[] { 0, 1, 2, 3, 4 };
    private static DatagramPacket expectedPacket = new DatagramPacket(expectedBytes, expectedBytes.length);
    private static DatagramPacket emptyPacket = new DatagramPacket(new byte[0], 0);

    @Test
    void testEqualsSelf() throws Exception
    {
        final DatagramPacketPerData data = new DatagramPacketPerData(expectedPacket);
        
        assertEquals(data, data);
    }
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new DatagramPacketPerData(expectedPacket).equals(null));
    }
    
    @Test
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new DatagramPacketPerData(expectedPacket).equals(new DatagramPacketPerData(emptyPacket)));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new DatagramPacketPerData(expectedPacket).equals(new Object()));
    }
    
    @Test
    void testBytesPreserved() throws Exception
    {
        final DatagramPacketPerData data = new DatagramPacketPerData(expectedBytes);
        assertArrayEquals(expectedBytes, data.getPerData());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<DatagramPacketPerData> set = new HashSet<>();
        final DatagramPacketPerData data1 = new DatagramPacketPerData(expectedPacket);
        final DatagramPacketPerData data2 = new DatagramPacketPerData(emptyPacket);
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
        assertFalse(set.contains(data2));
    }
    
    @Test
    @Disabled
    void testNullBytes() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new DatagramPacketPerData((byte[])null));
    }
    
    @Test
    @Disabled
    void testNullPacket() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new DatagramPacketPerData((DatagramPacket)null));
    }
}
