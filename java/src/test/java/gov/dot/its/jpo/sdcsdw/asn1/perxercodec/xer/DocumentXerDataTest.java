package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;

class DocumentXerDataTest
{
    
    private static final String expectedXml = "<a>b</a>";
    
    private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private static final DocumentBuilder documentBuilder;
    private static final Document expectedDocument;
    private static final Document otherDocument;
    static {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            expectedDocument = documentBuilder.parse(new ByteArrayInputStream(expectedXml.getBytes()));
            otherDocument = documentBuilder.parse(new ByteArrayInputStream("<b>a</b>".getBytes()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
    }
    
    @Test
    @Disabled
    void testEmptyString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new DocumentXerData(""));
    }
    
    @Test
    void testEqualsSelf() throws Exception
    {
        final DocumentXerData data = new DocumentXerData(expectedDocument);
        
        assertEquals(data, data);
    }
    
    
    @Test
    void testNotEqualsNull() throws Exception
    {
        assertFalse(new DocumentXerData(expectedDocument).equals(null));
    }
    
    @Test
    void testEqualsSameData() throws Exception
    {
        assertEquals(new DocumentXerData(expectedXml), new DocumentXerData(expectedXml));
    }
    
    @Test
    @Disabled
    void testNotEqualsDifferentData() throws Exception
    {
        assertFalse(new DocumentXerData(expectedDocument).equals(new DocumentXerData(otherDocument)));
    }
    
    @Test
    void testNotEqualsDifferentType() throws Exception
    {
        assertFalse(new DocumentXerData(expectedDocument).equals(new Object()));
    }
    
    @Test
    void testXmlStringPreserved() throws Exception
    {
        final DocumentXerData data = new DocumentXerData(expectedXml);
        assertEquals(expectedXml, data.getXerData());
    }
    
    @Test
    void testDocumentPreserved() throws Exception
    {
        assertEquals(expectedDocument, new DocumentXerData(expectedDocument).getFormattedXerData());
    }
    
    @Test
    void testUseAsHashKey() throws Exception
    {
        final HashSet<DocumentXerData> set = new HashSet<>();
        final DocumentXerData data2 = new DocumentXerData(expectedXml);
        final DocumentXerData data1 = new DocumentXerData(otherDocument);
        
        set.add(data1);
        
        assertTrue(set.contains(data1));
        assertFalse(set.contains(data2));
    }
    
    @Test
    @Disabled
    void testNullString() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new DocumentXerData((String)null));
    }
    
    @Test
    @Disabled
    void testNullDocument() throws Exception
    {
        assertThrows(IllegalArgumentException.class, () -> new DocumentXerData((Document)null));
    }
   
}
