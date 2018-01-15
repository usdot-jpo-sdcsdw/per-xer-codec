package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

class SaxXmlReaderXerDataTest
{
private static final String expectedXml = "<a>b</a>";
    
    private static final XMLReader expectedReader;
    private static final XMLReader otherReader;
    static {
        try {
            expectedReader = XMLReaderFactory.createXMLReader();
            expectedReader.parse(new InputSource(new ByteArrayInputStream(expectedXml.getBytes())));
            otherReader = XMLReaderFactory.createXMLReader();
            otherReader.parse(new InputSource(new ByteArrayInputStream("<b>a</b>".getBytes())));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
    @Test
    @Disabled
    void testEmptyString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new SaxXmlReaderXerData(""));
    }
    
    @Test
    void testEqualsSelf() throws Exception
    {
        final SaxXmlReaderXerData data = new SaxXmlReaderXerData(expectedXml);
        
        assertEquals(data, data);
    }
    
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new SaxXmlReaderXerData(expectedXml).equals(null));
    }
    
    @Test
    @Disabled
    void testEqualsSameData() throws Exception
    {
        assertEquals(new SaxXmlReaderXerData(expectedXml), new SaxXmlReaderXerData(expectedXml));
    }
    
    @Test
    @Disabled
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new SaxXmlReaderXerData(expectedXml).equals(new SaxXmlReaderXerData("<b>a</b>")));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new SaxXmlReaderXerData(expectedXml).equals(new Object()));
    }
    
    @Test
    void testXmlStringPreserved() throws Exception
    {
        final SaxXmlReaderXerData data = new SaxXmlReaderXerData(expectedXml);
        assertEquals(expectedXml, data.getXerData());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<SaxXmlReaderXerData> set = new HashSet<>();
        final SaxXmlReaderXerData data1 = new SaxXmlReaderXerData(expectedXml);
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
    }
    
    @Test
    @Disabled
    void testNullString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new SaxXmlReaderXerData((String)null));
    }
   
}
