package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

class AdvisorySituationDataTest
{

    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.AdvisorySituationDataType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestAdvisorySituationData);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestAdvisorySituationData);
            }
        }
    }
    
    @Test
    void testGuessPerToXer() throws Exception
    {
        TestData.assertPerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.AdvisorySituationDataType, TestData.HexPerTestAdvisorySituationData);
    }
    
    
    @Test
    void testXerToPer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.AdvisorySituationDataType)) {
                TestData.assertXerDatumParses(type, TestData.RawXerTestAdvisorySituationData);
            } else {
                TestData.assertXerDatumFails(type, TestData.RawXerTestAdvisorySituationData);
            }
        }
    }
    
    @Test
    void testGuessXerToPer() throws Exception
    {
        TestData.assertXerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.AdvisorySituationDataType, TestData.RawXerTestAdvisorySituationData);
    }
}
