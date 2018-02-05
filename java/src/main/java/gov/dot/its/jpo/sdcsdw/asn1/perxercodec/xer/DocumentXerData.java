package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;

/** XER data which is encoded as a javax XML document
 * 
 * @author andrew
 *
 */
public class DocumentXerData implements XerData<Document>
{
	/**
	 * Build a XER data object from a javax XML document 
	 * @param documentXerData XER data encoded as a javax XML document
	 */
    public DocumentXerData(Document xerDocument) throws UnformattingFailedException
    {
        if (xerDocument == null) {
            throw new IllegalArgumentException("xerDocument cannot be null");
        }
        
        this.documentXerData = xerDocument;
        this.xerData = documentToString(xerDocument);
    }
    
    /**
     * Formatter for this type
     */
    public static final XerDataFormatter<Document, DocumentXerData> formatter = DocumentXerData::new;
    /**
     * Unformatter for this type
     */
    public static final XerDataUnformatter<Document, DocumentXerData> unformatter = DocumentXerData::new;
    
    /**
     * Build a XER data object from a string containing XML
     * @param xerData String containing XML
     * @throws FormattingFailedException If the string could not be parsed as XML
     */
    public DocumentXerData(String xerData) throws FormattingFailedException
    {
        if (xerData == null) {
            throw new IllegalArgumentException("xerData cannot be null");
        } else if (xerData == "") {
            throw new IllegalArgumentException("xerData cannot be empty");
        }
        
        this.xerData = xerData;
        
        try {
            InputStream xerStream = new ByteArrayInputStream(xerData.getBytes());
			this.documentXerData = documentBuilder.parse(xerStream);
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
        if (obj instanceof XerData<?>) {
            return ((XerData<?>) obj).getXerData().equals(xerData);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return xerData;
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
    
    private static String documentToString(Document doc)
    {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting document to String", ex);
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
