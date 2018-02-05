package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

class AdvisorySituationDataDistributionTest
{
    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.AdvisorySituationDataDistributionType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestAdvisorySituationDataDistribution);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestAdvisorySituationDataDistribution);
            }
        }
    }
    
    @Test
    void testGuessPerToXer() throws Exception
    {
        TestData.assertPerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.AdvisorySituationDataDistributionType, TestData.HexPerTestAdvisorySituationDataDistribution);
    }

    @Test
    void testXerToPer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.AdvisorySituationDataDistributionType)) {
                TestData.assertXerDatumParses(type, TestData.RawXerTestAdvisorySituationDataDistribution);
            } else {
                TestData.assertXerDatumFails(type, TestData.RawXerTestAdvisorySituationDataDistribution);
            }
        }
    }
    
    @Test
    void testGuessXerToPer() throws Exception
    {
        TestData.assertXerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.AdvisorySituationDataDistributionType, TestData.RawXerTestAdvisorySituationDataDistribution);
    }
}
