package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

class AdvisorySituationDataTest
{

    @Test
    void test() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.AdvisorySituationDataType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestAdvisorySituationData);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestAdvisorySituationData);
            }
        }
    }

}
