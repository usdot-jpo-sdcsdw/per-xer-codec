package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ByteBufferPerDataTest
{

    private static ByteBuffer expectedBytes = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4 });
    private static ByteBuffer emptyBuffer = ByteBuffer.allocate(0);

    @Test
    void testEqualsSelf() throws Exception
    {
        final ByteBufferPerData data = new ByteBufferPerData(expectedBytes);
        
        assertEquals(data, data);
    }
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new ByteBufferPerData(expectedBytes).equals(null));
    }
    
    @Test
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new ByteBufferPerData(expectedBytes).equals(new ByteBufferPerData(emptyBuffer)));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new ByteBufferPerData(expectedBytes).equals(new Object()));
    }
    
    @Test
    void testByteBufferStringPreserved() throws Exception
    {
        final ByteBufferPerData data = new ByteBufferPerData(expectedBytes);
        assertEquals(expectedBytes, data.getFormattedPerData());
        assertArrayEquals(expectedBytes.array(), data.getPerData());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<ByteBufferPerData> set = new HashSet<>();
        final ByteBufferPerData data1 = new ByteBufferPerData(expectedBytes);
        final ByteBufferPerData data2 = new ByteBufferPerData(emptyBuffer);
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
        assertFalse(set.contains(data2));
    }
    
    @Test
    void testNullBytes() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new ByteBufferPerData((byte[])null));
    }
}
