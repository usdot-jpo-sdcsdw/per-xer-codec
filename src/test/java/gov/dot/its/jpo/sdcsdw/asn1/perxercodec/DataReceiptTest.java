package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;

class DataReceiptTest
{
    
    private static final String HexPerTestInput = "19 80 00 00 00 38 15 38 86 c0";

    @Test
    void testPerToXer() throws Exception
    {
        PerXerCodec.perToXer(Asn1Types.DataReceiptType, HexPerTestInput, HexPerData.unformatter, XmlXerData.formatter);
    }

}
