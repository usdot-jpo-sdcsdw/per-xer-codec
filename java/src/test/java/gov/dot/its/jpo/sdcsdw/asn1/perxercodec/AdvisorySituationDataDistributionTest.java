package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

class AdvisorySituationDataDistributionTest
{
    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.AdvisorySituationDataDistributionType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestAdvisoryDataDistribution);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestAdvisoryDataDistribution);
            }
        }
    }

}
