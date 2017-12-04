package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;

class ServiceRequestTest
{
    
    private static final String HexPerTestInput = "8c 00 00 00 00 1c 0a 9c 43 6b 69 e0";

    @Test
    void testPerToXer() throws Exception
    {
        PerXerCodec.perToXer(Asn1Types.ServiceRequestType, HexPerTestInput, HexPerData.unformatter, XmlXerData.formatter);
    }

}
