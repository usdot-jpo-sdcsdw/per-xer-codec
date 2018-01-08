package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

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
