package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlXerData implements XerData
{

    public XmlXerData(Document documentXerData)
    {
        this.documentXerData = documentXerData;
        // TODO: deal with whitespace
        xerData = documentXerData.getTextContent();
    }
    
    public XmlXerData(String xerData) throws SAXException, IOException
    {
        this.xerData = xerData;
        this.documentXerData = documentBuilder.parse(xerData);
    }
    
    @Override
    public String getXerData()
    {
        return xerData;
    }
    
    public Document getDocumentXerData()
    {
        return documentXerData;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((documentXerData == null) ? 0 : documentXerData.hashCode());
        result = prime * result + ((xerData == null) ? 0 : xerData.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof XerData) {
            return ((XerData) obj).getXerData().equals(xerData);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return documentXerData.toString();
    }
    
    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    
    private static DocumentBuilder documentBuilder;
    
    static {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    private final String xerData;
    private final Document documentXerData;
}
