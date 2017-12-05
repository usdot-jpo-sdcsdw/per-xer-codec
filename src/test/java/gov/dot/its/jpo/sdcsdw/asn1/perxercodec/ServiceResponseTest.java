package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;

class ServiceResponseTest
{
    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.ServiceResponseType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestServiceResponse);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestServiceResponse);
            }
        }
    }

}
