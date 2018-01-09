package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec.TypeGuessResult;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.RawXerData;

class PerXerCodecTest
{

    @Test
    void testPerToXerNullType()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.perToXer(null, 
                                                TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                RawXerData.formatter));
    }
    
    @Test
    void testPerToXerNullData()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.perToXer(Asn1Types.AdvisorySituationDataType, 
                                                null,
                                                TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                RawXerData.formatter));
    }
    
    @Test
    void testPerToXerNullUnformatter()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.perToXer(Asn1Types.AdvisorySituationDataType, 
                                                TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                null,
                                                RawXerData.formatter));
    }
    
    @Test
    void testPerToXerNullFormatter()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.perToXer(Asn1Types.AdvisorySituationDataType, 
                                                TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                null));
    }
    
    @Test
    void testGuessPerToXerNullTypes()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.guessPerToXer(null, 
                                                TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                null));
    }
    
    @Test
    void testGuessPerToXerNullData()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.guessPerToXer(Asn1Types.getAllTypes(),
                                                null,
                                                TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                RawXerData.formatter));
    }
    
    @Test
    void testGuessPerToXerNullUnformatter()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.guessPerToXer(Asn1Types.getAllTypes(),
                                                TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                null,
                                                RawXerData.formatter));
    }
    
    @Test
    void testGuessPerToXerNullFormatter()
    {
        assertThrows(IllegalArgumentException.class, 
                     () -> PerXerCodec.guessPerToXer(Asn1Types.getAllTypes(), 
                                                TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                null));
    }

    @Test
    void testGuessPerToXerNoTypes() throws Exception
    {
        TypeGuessResult<String> result = PerXerCodec.guessPerToXer(new ArrayList<Asn1Type>(), 
                                                                   TestData.HexPerTestAdvisorySituationData.getTestInput(),
                                                                   TestData.HexPerTestAdvisorySituationData.getUnformatter(),
                                                                   RawXerData.formatter);
        
        assertFalse(result.isSuccesful());
        assertNull(result.getData());
        assertNull(result.getType());
    }
    
}
