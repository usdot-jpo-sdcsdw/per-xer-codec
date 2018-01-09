package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;


import org.junit.jupiter.api.Test;

class DataAcceptanceTest
{
    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.DataAcceptanceType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestDataAcceptance);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestDataAcceptance);
            }
        }
    }

    @Test
    void testGuessPerToXer() throws Exception
    {
        TestData.assertPerDatumParsesAs(Asn1Types.getAllTypes(), Asn1Types.DataAcceptanceType, TestData.HexPerTestDataAcceptance);
    }
}
