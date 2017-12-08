package gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;

public class SaxXmlReaderXerData implements XerData<XMLReader>
{
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
    
    private final String xerData;
    private final XMLReader xmlReader;
}
