package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

class Base64PerDataTest
{
    private static final byte[] expectedBytes = new byte[] {0, 1, 2, 3, 4};
    private static final String expectedBase64 = DatatypeConverter.printBase64Binary(expectedBytes);
    
    @Test
    void testValid() throws Exception
    {
        assertArrayEquals(expectedBytes, new Base64PerData(expectedBase64).getPerData());
    }
    
    @Test
    void testInvalid() throws Exception
    {
        assertThrows(UnformattingFailedException.class, () -> new Base64PerData("!!!this is not valid base 64"));
    }
    
    @Test
    void testRaw() throws Exception
    {
        assertArrayEquals(expectedBytes, new Base64PerData(expectedBytes).getPerData());
    }
    
    @Test
    void testEqualsSelf() throws Exception
    {
        final Base64PerData data = new Base64PerData("");
        
        assertEquals(data, data);
    }
    
    @Test
    void testEqualsSameMeaning() throws Exception
    {
        assertEquals(new HexPerData(expectedBytes), new Base64PerData(expectedBase64));
    }
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new Base64PerData("").equals(null));
    }
    
    @Test
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new Base64PerData("").equals(new Base64PerData(expectedBase64)));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new Base64PerData("").equals(new Object()));
    }
    
    @Test
    void testHexStringPreserved() throws Exception
    {
        final Base64PerData data = new Base64PerData(expectedBase64);
        assertEquals(expectedBase64, data.getFormattedPerData());
        assertEquals(expectedBase64, data.toString());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<Base64PerData> set = new HashSet<>();
        final Base64PerData data1 = new Base64PerData(expectedBase64);
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
    }
    
    @Test
    void testNullString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new Base64PerData((String)null));
    }
    
    @Test
    void testNullBytes() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new Base64PerData((byte[])null));
    }
   
}
