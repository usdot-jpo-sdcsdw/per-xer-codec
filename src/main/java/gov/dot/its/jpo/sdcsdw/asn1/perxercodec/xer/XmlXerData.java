package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

/**
 * XER data which is encoded as a javax XML document
 * @author andrew
 *
 */
public class XmlXerData implements XerData<Document>
{
	/**
	 * Build a XER data object from a javax XML document 
	 * @param documentXerData
	 */
    public XmlXerData(Document documentXerData)
    {
        this.documentXerData = documentXerData;
        // TODO: deal with whitespace
        xerData = documentXerData.getTextContent();
    }
    
    public static final XerDataFormatter<Document, XmlXerData> formatter = XmlXerData::new;
    public static final XerDataUnformatter<Document, XmlXerData> unformatter = XmlXerData::new;
    
    /**
     * Build a XER data object from a string containing XML
     * @param xerData String containing XML
     * @throws UnformattingFailedException If the string could not be parsed as XML
     */
    public XmlXerData(String xerData) throws FormattingFailedException
    {
        this.xerData = xerData;
        try {
			this.documentXerData = documentBuilder.parse(xerData);
		} catch (SAXException e) {
			throw new FormattingFailedException("Could not parse as XML", e);
		} catch (IOException e) {
			throw new FormattingFailedException("An IO error occured while parsing XML", e);
		}
    }
    
    @Override
    public String getXerData()
    {
        return xerData;
    }
    
    @Override
    public Document getFormattedXerData()
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
    
    /**
     * Builder factory needed to get documentBuilder
     */
    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    
    /**
     * Document builder used to build documents for all instances of this class
     */
    private static DocumentBuilder documentBuilder;
    
    static {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    /**
     * XER data as a string
     */
    private final String xerData;
    
    /**
     * XER data as a javax XML document
     */
    private final Document documentXerData;
}
