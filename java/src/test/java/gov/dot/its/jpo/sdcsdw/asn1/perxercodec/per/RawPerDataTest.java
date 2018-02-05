package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RawPerDataTest
{
    
    private static byte[] expectedBytes = { 0, 1, 2, 3, 4 };

    @Test
    void testEqualsSelf() throws Exception
    {
        final RawPerData data = new RawPerData(expectedBytes);
        
        assertEquals(data, data);
    }
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new RawPerData(expectedBytes).equals(null));
    }
    
    @Test
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new RawPerData(expectedBytes).equals(new RawPerData(new byte[0])));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new RawPerData(expectedBytes).equals(new Object()));
    }
    
    @Test
    void testRawStringPreserved() throws Exception
    {
        final RawPerData data = new RawPerData(expectedBytes);
        assertArrayEquals(expectedBytes, data.getFormattedPerData());
        assertArrayEquals(expectedBytes, data.getPerData());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<RawPerData> set = new HashSet<>();
        final RawPerData data1 = new RawPerData(expectedBytes);
        final RawPerData data2 = new RawPerData(new byte[0]);
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
        assertFalse(set.contains(data2));
    }
    
    @Test
    void testNullBytes() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new RawPerData((byte[])null));
    }

}
