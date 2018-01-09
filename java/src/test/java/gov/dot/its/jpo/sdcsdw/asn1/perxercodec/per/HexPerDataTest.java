package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

class HexPerDataTest
{
    
    private static final byte[] expectedBytes = new byte[] {0, 1, 2, 3, 4};

    @Test
    void testEmptyString() throws Exception
    {
        assertArrayEquals(new byte[0], new HexPerData("").getPerData());
    }
    
    @Test
    void testRaw() throws Exception
    {
        assertArrayEquals(expectedBytes, new HexPerData(expectedBytes).getPerData());
    }
    
    @Test
    void testNoSpaces() throws Exception
    {
        assertArrayEquals(expectedBytes, new HexPerData("0001020304").getPerData());
    }
    
    @Test
    void testNoSpacesWithPrefix() throws Exception
    {
        assertArrayEquals(expectedBytes, new HexPerData("0x0001020304").getPerData());
    }
    
    @Test
    void testWithSpaces() throws Exception
    {
        assertArrayEquals(expectedBytes, new HexPerData("00 01 02 03 04").getPerData());
    }
    
    @Test
    void testWithPrefixes() throws Exception
    {
        assertArrayEquals(expectedBytes, new HexPerData("0x00 0x01 0x02 0x03 0x04").getPerData());
    }
    
    @Test
    void testMixedPrefixes() throws Exception
    {
        assertThrows(UnformattingFailedException.class, () -> new HexPerData("00 0x01"));
    }
    
    @Test
    void testOddDigits() throws Exception
    {
        assertThrows(UnformattingFailedException.class, () -> new HexPerData("00012"));
    }
    
    @Test
    void testMalformedWithPrefixes() throws Exception
    {
        assertThrows(UnformattingFailedException.class, () -> new HexPerData("0x000 0x01"));
    }
    
    @Test
    void testEqualsSelf() throws Exception
    {
        final HexPerData data = new HexPerData("");
        
        assertEquals(data, data);
    }
    
    @Test
    void testEqualsSameMeaning() throws Exception
    {
        assertEquals(new HexPerData("0001020304"), new HexPerData("00 01 02 03 04"));
    }
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new HexPerData("").equals(null));
    }
    
    @Test
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new HexPerData("").equals(new HexPerData("00")));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new HexPerData("").equals(new Object()));
    }
    
    @Test
    void testHexStringPreserved() throws Exception
    {
        final String hexString = "00 01 02 03 04";
        final HexPerData data = new HexPerData(hexString);
        assertEquals(hexString, data.getFormattedPerData());
        assertEquals(hexString, data.toString());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<HexPerData> set = new HashSet<>();
        final HexPerData data1 = new HexPerData("00 01 02 03 04");
        final HexPerData data2 = new HexPerData("0001020304");
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
        assertFalse(set.contains(data2));
    }
    
    @Test
    void testNullString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new HexPerData((String)null));
    }
    
    @Test
    void testNullBytes() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new HexPerData((byte[])null));
    }

}
