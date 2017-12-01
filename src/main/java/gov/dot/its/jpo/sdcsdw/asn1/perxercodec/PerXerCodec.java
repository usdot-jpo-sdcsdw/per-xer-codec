package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.Function;

import javax.xml.bind.DatatypeConverter;

/** Codec for translating ASN.1 J2735 (+ Leidos extensions) between UPER and XER
 * 
 * Currently supports the following types:
 * ServiceRequest
 * ServiceResponse
 * DataRequest
 * AdvisorySituationDataDistribution
 * DataAcceptance
 * DataReceipt
 * AdvisorySituationData
 * 
 * 
 * The basic use-case of this class is to pick one of the ASN.1 type values (e.g. ServiceRequestType), 
 * and pass it along with PER or XER data to perToXer or xerToPer respectively
 */
public class PerXerCodec
{
    /** The base name of the native library, which have prefixes and extensions added based on the OS
     * 
     */
    private static String nativeLibraryName = "perxercodec";
    
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
         * @param cInt
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
    public static final Asn1Type DataRequestType = new Asn1Type(nativeGetDataRequestType());
    public static final Asn1Type AdvisorySituationDataDistributionType = new Asn1Type(nativeGetAdvisorySituationDataDistributionType());
    public static final Asn1Type DataAcceptanceType = new Asn1Type(nativeGetDataAcceptanceType());
    public static final Asn1Type DataReceiptType = new Asn1Type(nativeGetDataReceiptType());
    
    /** Convert PER encoded data into XER encoded data 
     * 
     * @param type The type the PER encoded data contains
     * @param per The PER encoded data
     * @return The XER encoded data, or null if the process failed
     */
    public static <T extends XerData> T perToXer(Asn1Type type, PerData per, Function<String, T> buildXer)
    {
        String xer = nativePerToXer(type.cInt, per.getPerData());
        if (xer == null) {
            return null;
        } else {
            return buildXer.apply(xer);
        }
    }
    
    /** Convert XER encoded data into PER encoded data 
     * 
     * @param type The type the XER encoded data contains
     * @param per The XER encoded data
     * @return The PER encoded data, or null if the process failed
     */
    public static <T extends PerData> T xerToPer(Asn1Type type, XerData xer, Function<byte[], T> buildPer)
    {
        byte[] per = nativeXerToPer(type.cInt, xer.getXerData());
        if (per == null) {
            return null;
        } else {
            return buildPer.apply(per);
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

    // These native methods just return the coresponding native enum values
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
