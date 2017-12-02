package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.CodecFailedException;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.FormattingFailedException;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.UnformattingFailedException;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataFormatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataFormatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataUnformatter;

/**
 * Class containing static methods for the codec  
 * 
 */
public class PerXerCodec
{
    /** The base name of the native library, which have prefixes and extensions added based on the OS
     * 
     */
    private static String nativeLibraryName = "per-xer-codec";
    
    /* Static block to initialize the native library
     * 
     */
    static {
        try {
            // Try to load the library normally
            System.out.println(System.getProperty("java.library.path"));
            System.loadLibrary(nativeLibraryName);
        } catch (UnsatisfiedLinkError ex) {
            try {
                // If we can't find it
                
                // Figure out what java thinks the file should be called, and pull it out of the jar into a temporary file
                
                String expectedFilename = System.mapLibraryName(nativeLibraryName);
                String expectedFileExtension = expectedFilename.substring(expectedFilename.indexOf('.'));
                Class<PerXerCodec> thisClass = PerXerCodec.class;
                String thisClassQualifiedName = thisClass.getCanonicalName();
                String libraryResourcePath = thisClassQualifiedName.replace('.', '/') + "/" + expectedFilename;
                InputStream jarLibraryStream = PerXerCodec.class.getClassLoader().getResourceAsStream(libraryResourcePath);
                File libraryFile = File.createTempFile("lib", expectedFileExtension);
                String libraryPathString = libraryFile.getAbsolutePath();
                Path libraryPath = Paths.get(libraryPathString);
                Files.copy(jarLibraryStream, libraryPath, StandardCopyOption.REPLACE_EXISTING);
                libraryFile.deleteOnExit();
                
                // Try again, with the temporary file
                
                System.load(libraryPathString);
            } catch (Exception ex2) {
                
                // If we still can't find it, give up
                
                throw new RuntimeException("Could not extract native shared library", ex2);
            }
        }
    }
    
    /** Opaque type for selecting which type to encode or decode as 
     */
    public static class Asn1Type
    {
        /** Construct an ASN.1 type from the native enum value 
         * 
         * @param cInt Native enum value for the type
         */
        private Asn1Type(int cInt)
        {
            this.cInt = cInt;
        }
        
        /** Native enum value of this type
         * 
         */
        private final int cInt;
    }
    
    /** Type for Advisory Situation Data messages
     * 
     */
    public static final Asn1Type AdvisorySituationDataType = new Asn1Type(nativeGetAdvisorySituationDataType());
    
    /** Type for Service Request messages
     * 
     */
    public static final Asn1Type ServiceRequestType = new Asn1Type(nativeGetServiceRequestType());
    
    /** Type for Service Response messages
     * 
     */
    public static final Asn1Type ServiceResponseType = new Asn1Type(nativeGetServiceResponseType());
    
    /** Type for Data Request messages
     * 
     */
    public static final Asn1Type DataRequestType = new Asn1Type(nativeGetDataRequestType());
    
    /** Type for Advisory Situation Data Distribution messages
     * 
     */
    public static final Asn1Type AdvisorySituationDataDistributionType = new Asn1Type(nativeGetAdvisorySituationDataDistributionType());
    
    /** Type for Data Acceptance messages
     * 
     */
    public static final Asn1Type DataAcceptanceType = new Asn1Type(nativeGetDataAcceptanceType());
    
    /** Type for Data Receipt messages
     * 
     */
    public static final Asn1Type DataReceiptType = new Asn1Type(nativeGetDataReceiptType());
    
    /** Convert PER encoded data into XER encoded data 
     * 
     * @param type The type the PER encoded data contains
     * @param per The PER encoded data
     * @param xerFormatter How to build the representation of the output PER data
     * @return The XER encoded data, or null if the process failed
     * @throws CodecFailedException If the generated ASN.1 code could not handle the given data
     * @throws FormattingFailedException If xerBuilder could not build the desired representation of the output
     * @throws UnformattingFailedException 
     */
    public static <PER, PerT extends PerData<PER>, XER, XerT extends XerData<XER>>
    XER perToXer(Asn1Type type,
    		     PER per,
    		     PerDataUnformatter<PER, PerT> perUnformatter,
    		     XerDataFormatter<XER, XerT> xerFormatter)
        throws CodecFailedException, FormattingFailedException, UnformattingFailedException
    {
    	PerT rawPer = perUnformatter.unformatPerData(per);
    	
        String rawXer = nativePerToXer(type.cInt, rawPer.getPerData());
        if (rawXer == null) {
            throw new CodecFailedException("Could not convert PER data to XER: + " + per);
        } else {
            return xerFormatter.formatXerData(rawXer).getFormattedXerData();
        }
    }
    
    /** Convert XER encoded data into PER encoded data 
     * 
     * @param type The type the XER encoded data contains
     * @param xer The XER encoded data
     * @param perFormatter How to build the representation of the output XER
     * @return The PER encoded data, or null if the process failed
     * @throws CodecFailedException If the generated ASN.1 code could not handle the given data
     * @throws FormattingFailedException If perBuilder could not build the desired representation of the output
     */
    public static <XER, XerT extends XerData<XER>, PER, PerT extends PerData<PER>>
    PER xerToPer(Asn1Type type, 
    			 XER xer, 
    			 XerDataUnformatter<XER, XerT> xerUnformatter, 
    			 PerDataFormatter<PER, PerT> perFormatter)
    	throws UnformattingFailedException, CodecFailedException, FormattingFailedException
    {
    	
    	XerT rawXer = xerUnformatter.unformatXerData(xer);
    	
        byte[] rawPer = nativeXerToPer(type.cInt, rawXer.getXerData());
        if (rawPer == null) {
            throw new CodecFailedException("Could not convert XER data to PER: " + xer);
        } else {
            return perFormatter.formatPerData(rawPer).getFormattedPerData();
        }
    }
    
    /** Get the ASN.1 type object based on the type's name
     * 
     * @param name The type's name, in UpperCamelCase
     * @return The ASN.1 type object, or null if no type with that name exists
     */
    public static Asn1Type getAsn1TypeByName(String name)
    {
        switch (name)
        {
        case "ServiceRequest":
            return ServiceRequestType;
        case "ServiceResponse":
            return ServiceResponseType;
        case "DataRequest":
            return DataRequestType;
        case "AdvisorySituationDataDistribution":
            return AdvisorySituationDataDistributionType;
        case "DataAcceptance":
            return DataAcceptanceType;
        case "DataReceipt":
            return DataReceiptType;
        default:
            return null;
        }
    }

    // These native methods just return the corresponding native enum values
    private native static int nativeGetAdvisorySituationDataType();    
    private native static int nativeGetServiceRequestType();
    private native static int nativeGetServiceResponseType();
    private native static int nativeGetDataRequestType();
    private native static int nativeGetAdvisorySituationDataDistributionType();
    private native static int nativeGetDataAcceptanceType();
    private native static int nativeGetDataReceiptType();
    
    // These native methods map to the underlying functions for conversion
    private native static String nativePerToXer(int type, byte[] per);
    private native static byte[] nativeXerToPer(int type, String xer);
}
