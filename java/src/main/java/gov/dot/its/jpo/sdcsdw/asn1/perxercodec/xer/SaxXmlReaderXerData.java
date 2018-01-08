package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;

/** XER data which is encoded as SAX XML Reader
 * 
 * @author amm30955
 *
 */
public class SaxXmlReaderXerData implements XerData<XMLReader>
{
    /** Build a XER data object from an XML string
     * 
     * @param xerData String containing XER data
     * @throws FormattingFailedException If the string could not be parsed as XML
     */
    public SaxXmlReaderXerData(String xerData) throws FormattingFailedException
    {
        this.xerData = xerData;
        try {
            xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.parse(new InputSource(new ByteArrayInputStream(xerData.getBytes())));
        } catch (SAXException e) {
            throw new FormattingFailedException("Could not parse XML", e);
        } catch (IOException e) {
            throw new FormattingFailedException("Could not parse XML", e);
        }
    }
    
    @Override
    public String getXerData()
    {
        return xerData;
    }

    @Override
    public XMLReader getFormattedXerData()
    {
        return xmlReader;
    }
    
    /** XER data as a string  */
    private final String xerData;
    
    /** XER data as an XML reader */
    private final XMLReader xmlReader;
}
