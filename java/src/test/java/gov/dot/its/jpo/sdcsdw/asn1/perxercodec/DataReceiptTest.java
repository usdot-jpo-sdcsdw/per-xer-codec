package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import org.junit.jupiter.api.Test;

class DataReceiptTest
{
    @Test
    void testPerToXer() throws Exception
    {
        for (Asn1Type type : Asn1Types.getAllTypes()) {
            if (type.equals(Asn1Types.DataReceiptType)) {
                TestData.assertPerDatumParses(type, TestData.HexPerTestDataReceipt);
            } else {
                TestData.assertPerDatumFails(type, TestData.HexPerTestDataReceipt);
            }
        }
    }

}
