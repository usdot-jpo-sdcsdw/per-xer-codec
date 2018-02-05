package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import org.junit.jupiter.api.function.Executable;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.RawXerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataUnformatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec.TypeGuessResult;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.CodecFailedException;

/** Repository for sample data
 * 
 * @author amm30955
 *
 */
public class TestData
{
    /** A single test case with PER data
     * 
     * @author amm30955
     *
     * @param <T> Test Data type
     * @param <PerT> PerData type containing the test data
     */
    public static class PerTestDatum<T, PerT extends PerData<T>> 
    {
        /** Create a test case
         *  
         * @param testInput Test data
         * @param unformatter Unformatter for the data
         */
        public PerTestDatum(T testInput,
                            PerDataUnformatter<T, PerT> unformatter)
        {
            super();
            this.testInput = testInput;
            this.unformatter = unformatter;
        }
        
        /** Get the test data 
         * 
         * @return Test data
         */
        public T getTestInput()
        {
            return testInput;
        }
        
        /** Get the unformatter for the test data
         * 
         * @return Unformatter for the test data
         */
        public PerDataUnformatter<T, PerT> getUnformatter()
        {
            return unformatter;
        }
        
        /** Test data */
        private final T testInput;
        /** Unformatter for the data */
        private final PerDataUnformatter<T, PerT> unformatter;
    }
    
    /** A single test case with XER data
     * 
     * @author amm30955
     *
     * @param <T> Test Data type
     * @param <XerT> XerData type containing the test data
     */
    public static class XerTestDatum<T, XerT extends XerData<T>> 
    {
        /** Create a test case
         * 
         * @param testInput Test data
         * @param unformatter Unformatter for the data
         */
        public XerTestDatum(T testInput,
                            XerDataUnformatter<T, XerT> unformatter)
        {
            super();
            this.testInput = testInput;
            this.unformatter = unformatter;
        }
        
        /** Get the test data 
         * 
         * @return Test data
         */
        public T getTestInput()
        {
            return testInput;
        }
        
        /** Get the unformatter for the test data
         * 
         * @return Unformatter for the test data
         */
        public XerDataUnformatter<T, XerT> getUnformatter()
        {
            return unformatter;
        }
        
        /** Test data */
        private final T testInput;
        /** Unformatter for the data */
        private final XerDataUnformatter<T, XerT> unformatter;
    }
    
    /** Test ASD data */
    public static final PerTestDatum<String, HexPerData> HexPerTestAdvisorySituationData;
    /** Test Service Request */
    public static final PerTestDatum<String, HexPerData> HexPerTestServiceRequest;
    /** Test Service Response */
    public static final PerTestDatum<String, HexPerData> HexPerTestServiceResponse;
    /** Test Data Request for Advisory Data */
    public static final PerTestDatum<String, HexPerData> HexPerTestDataRequest;
    /** Test Advisory Data Distribution */
    public static final PerTestDatum<String, HexPerData> HexPerTestAdvisorySituationDataDistribution;
    /** Test Data Acceptance */
    public static final PerTestDatum<String, HexPerData> HexPerTestDataAcceptance;
    /** Test Data Receipt */
    public static final PerTestDatum<String, HexPerData> HexPerTestDataReceipt;
    
    public static final XerTestDatum<String, RawXerData> RawXerTestAdvisorySituationDataDistribution;
    public static final XerTestDatum<String, RawXerData> RawXerTestAdvisorySituationData;
    public static final XerTestDatum<String, RawXerData> RawXerTestServiceRequest;
    public static final XerTestDatum<String, RawXerData> RawXerTestServiceResponse;
    public static final XerTestDatum<String, RawXerData> RawXerTestDataRequest;
    public static final XerTestDatum<String, RawXerData> RawXerTestDataAcceptance;
    public static final XerTestDatum<String, RawXerData> RawXerTestDataReceipt;
    
    
    static PerTestDatum<String, HexPerData> loadHexPerTest(String filename) throws IOException, URISyntaxException
    {
        String per = new String(Files.readAllBytes(Paths.get(TestData.class.getResource(filename).toURI())));
        
        return new PerTestDatum<String, HexPerData>(per, HexPerData.unformatter);
    }
    
    static XerTestDatum<String, RawXerData> loadRawXerTest(String filename) throws IOException, URISyntaxException
    {
        String xer = new String(Files.readAllBytes(Paths.get(TestData.class.getResource(filename).toURI())));
        
        return new XerTestDatum<String, RawXerData>(xer, RawXerData.unformatter);
        
    }
    static {
        try {
            RawXerTestAdvisorySituationDataDistribution = loadRawXerTest("AdvisorySituationDataDistribution_test_1.xml");
            RawXerTestAdvisorySituationData = loadRawXerTest("AdvisorySituationData_test_1.xml");
            RawXerTestServiceRequest = loadRawXerTest("ServiceRequest_test_1.xml");
            RawXerTestServiceResponse = loadRawXerTest("ServiceResponse_test_1.xml");
            RawXerTestDataRequest = loadRawXerTest("DataRequest_test_1.xml");
            RawXerTestDataAcceptance = loadRawXerTest("DataAcceptance_test_1.xml");
            RawXerTestDataReceipt = loadRawXerTest("DataReceipt_test_1.xml");
            
            HexPerTestAdvisorySituationDataDistribution = loadHexPerTest("AdvisorySituationDataDistribution_test_1.hex");
            HexPerTestAdvisorySituationData = loadHexPerTest("AdvisorySituationData_test_1.hex");
            HexPerTestServiceRequest = loadHexPerTest("ServiceRequest_test_1.hex");
            HexPerTestServiceResponse = loadHexPerTest("ServiceResponse_test_1.hex");
            HexPerTestDataRequest = loadHexPerTest("DataRequest_test_1.hex");
            HexPerTestDataAcceptance = loadHexPerTest("DataAcceptance_test_1.hex");
            HexPerTestDataReceipt = loadHexPerTest("DataReceipt_test_1.hex");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    

    
    

    
    /** Run a PER test case and assert it parses correctly
     * 
     * @param type The type to parse as
     * @param datum The test case to run
     * @throws Exception If anything happens
     */
    public static <T, PerT extends PerData<T>> void assertPerDatumParses(Asn1Type type, PerTestDatum<T, PerT> datum) throws Exception
    {
        Executable exec = () -> PerXerCodec.perToXer(type, datum.getTestInput(), datum.getUnformatter(), RawXerData.formatter);
        assertAll("Expecting to parse as " + type.getName(), exec);
    }
    
    public static <T, PerT extends PerData<T>> void assertPerDatumParsesAs(Iterable<Asn1Type> types, Asn1Type type, PerTestDatum<T, PerT> datum) throws Exception
    {
        TypeGuessResult<String> result = PerXerCodec.guessPerToXer(types, datum.getTestInput(), datum.getUnformatter(), RawXerData.formatter);
        
        assertTrue(result.isSuccesful());
        assertEquals(type, result.getType());
        assertNotNull(result.getData());
    }
    
    /** Run PER test case and assert it fails to parse
     * 
     * @param type The type to parse as
     * @param datum The test case to run
     * @throws Exception If anything happens
     */
    public static <T, PerT extends PerData<T>> void assertPerDatumFails(Asn1Type type, PerTestDatum<T, PerT> datum) throws Exception
    {
        Executable exec = () -> PerXerCodec.perToXer(type, datum.getTestInput(), datum.getUnformatter(), RawXerData.formatter);
        Supplier<String> failureMessageBuilder = () -> {
            try {
                String falseXer = PerXerCodec.perToXer(type, datum.getTestInput(), datum.getUnformatter(), RawXerData.formatter);
                return "Expecting not to parse as " + type.getName() + ", but got XER: " + falseXer;
            } catch (Exception e) {
                return "Dark magic has occured, the xer codec is not pure: " + e.getMessage();
            }
        };
        assertThrows(CodecFailedException.class, exec, failureMessageBuilder);
    }
    
    
    
    /** Run a XER test case and assert it parses correctly
     * 
     * @param type The type to parse as
     * @param datum The test case to run
     * @throws Exception If anything happens
     */
    public static <T, XerT extends XerData<T>> void assertXerDatumParses(Asn1Type type, XerTestDatum<T, XerT> datum) throws Exception
    {
        Executable exec = () -> PerXerCodec.xerToPer(type, datum.getTestInput(), datum.getUnformatter(), HexPerData.formatter);
        assertAll("Expecting to parse as " + type.getName(), exec);
    }
    
    public static <T, XerT extends XerData<T>> void assertXerDatumParsesAs(Iterable<Asn1Type> types, Asn1Type type, XerTestDatum<T, XerT> datum) throws Exception
    {
        TypeGuessResult<String> result = PerXerCodec.guessXerToPer(types, datum.getTestInput(), datum.getUnformatter(), HexPerData.formatter);
        
        assertTrue(result.isSuccesful());
        assertEquals(type, result.getType());
        assertNotNull(result.getData());
    }
    
    /** Run XER test case and assert it fails to parse
     * 
     * @param type The type to parse as
     * @param datum The test case to run
     * @throws Exception If anything happens
     */
    public static <T, XerT extends XerData<T>> void assertXerDatumFails(Asn1Type type, XerTestDatum<T, XerT> datum) throws Exception
    {
        Executable exec = () -> PerXerCodec.xerToPer(type, datum.getTestInput(), datum.getUnformatter(), HexPerData.formatter);
        Supplier<String> failureMessageBuilder = () -> {
            try {
                String falseXer = PerXerCodec.xerToPer(type, datum.getTestInput(), datum.getUnformatter(), HexPerData.formatter);
                return "Expecting not to parse as " + type.getName() + ", but got XER: " + falseXer;
            } catch (Exception e) {
                return "Dark magic has occured, the per codec is not pure: " + e.getMessage();
            }
        };
        assertThrows(CodecFailedException.class, exec, failureMessageBuilder);
    }
}
