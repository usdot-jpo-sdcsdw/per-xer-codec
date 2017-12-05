package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;

class ServiceRequestTest
{
    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.ServiceRequestType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestServiceRequest);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestServiceRequest);
            }
        }
    }

}
