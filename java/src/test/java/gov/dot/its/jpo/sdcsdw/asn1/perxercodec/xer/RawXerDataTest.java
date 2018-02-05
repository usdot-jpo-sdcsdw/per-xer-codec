package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RawXerDataTest
{
    @Test
    void testEqualsSelf() throws Exception
    {
        final RawXerData data = new RawXerData("<a/>");
        
        assertEquals(data, data);
    }
    
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new RawXerData("<a/>").equals(null));
    }
    
    @Test
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new RawXerData("<b/>").equals(new RawXerData("<a/>")));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new RawXerData("<a/>").equals(new Object()));
    }
    
    @Test
    void testHexStringPreserved() throws Exception
    {
        final String xmlString = "<a/>";
        final RawXerData data = new RawXerData(xmlString);
        assertEquals(xmlString, data.getFormattedXerData());
        assertEquals(xmlString, data.getXerData());
        assertEquals(xmlString, data.toString());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<RawXerData> set = new HashSet<>();
        final RawXerData data2 = new RawXerData("<a/>");
        final RawXerData data1 = new RawXerData("<b/>");
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
        assertFalse(set.contains(data2));
    }
    
    @Test
    void testNullString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new RawXerData((String)null));
    }
}
