package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

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

    @Test
    void testGuessPerToXer() throws Exception
    {
        TestData.assertPerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.ServiceResponseType, TestData.HexPerTestServiceResponse);
    }
    
    @Test
    void testXerToPer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.ServiceResponseType)) {
                TestData.assertXerDatumParses(type, TestData.RawXerTestServiceResponse);
            } else {
                TestData.assertXerDatumFails(type, TestData.RawXerTestServiceResponse);
            }
        }
    }

    @Test
    void testGuessXerToPer() throws Exception
    {
        TestData.assertXerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.ServiceResponseType, TestData.RawXerTestServiceResponse);
    }
}
