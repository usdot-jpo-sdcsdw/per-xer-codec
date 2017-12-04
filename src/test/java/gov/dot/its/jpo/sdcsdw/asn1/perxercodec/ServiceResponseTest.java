package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;

class ServiceResponseTest
{
    
    private static final String HexPerTestInput = "8c 20 00 00 00 1c 0a 9c 43 7f df 86 fb 30 80 08 9a 40 a4 f4 80 54 39 a5 16 fe 22 05 6e 42 20 15 40 f7 b5 af fa aa e6 c7 53 45 3f fd 0a be 84 b6 86 7c f3 e8 fa b9 fa 83 3c 0d 49 39 0e a3 a7 15 16 69 00";
    

    @Test
    void testPerToXer() throws Exception
    {
        PerXerCodec.perToXer(Asn1Types.ServiceResponseType, HexPerTestInput, HexPerData.unformatter, XmlXerData.formatter);
    }

}
