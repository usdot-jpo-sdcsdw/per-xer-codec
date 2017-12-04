package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;

class DataRequestTest
{
    private static final String HexPerTestInput = "0c 40 00 00 00 1c 0a 9c 43 62 93 d2 01 50 e6 94 5b f8 88 15 b9 08 80 55 03 de 04";
    
    @Test
    void testPerToXer() throws Exception
    {
        PerXerCodec.perToXer(Asn1Types.DataRequestType, HexPerTestInput, HexPerData.unformatter, XmlXerData.formatter);
    }

}
