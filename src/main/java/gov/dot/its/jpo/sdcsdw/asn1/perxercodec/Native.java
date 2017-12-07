package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Native methods and data
 * @author andrew
 *
 */
abstract class Native {
	
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
                Class<Native> thisClass = Native.class;
                String thisClassQualifiedName = thisClass.getCanonicalName();
                String libraryResourcePath = thisClassQualifiedName.replace('.', '/') + "/" + expectedFilename;
                InputStream jarLibraryStream = Native.class.getClassLoader().getResourceAsStream(libraryResourcePath);
                if (jarLibraryStream == null) {
                    throw new Exception("Native library was not present in jar (expected to be at " + libraryResourcePath + ")");
                }
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
	
	/**
	 * @return The native enum value for the Advisory Situation Data type
	 */
	native static int getAdvisorySituationDataType();

	/**
	 * @return The native enum value for the Service Request type
	 */
	native static int getServiceRequestType();

	/**
	 * @return The native enum value for the Service Response type
	 */
	native static int getServiceResponseType();

	/**
	 * @return The native enum value for the Data Request type
	 */
	native static int getDataRequestType();

	/**
	 * @return The native enum value for the Advisory Situation Data Distribution type
	 */
	native static int getAdvisorySituationDataDistributionType();

	/**
	 * @return The native enum value for the Data Acceptance type
	 */
	native static int getDataAcceptanceType();

	/**
	 * @return The native enum value for the Data Receipt type
	 */
	native static int getDataReceiptType();

	/**
	 * Natively convert PER data to XER
	 * @param type Native enum representing type to convert
	 * @param per PER data 
	 * @return XER data (null if failed)
	 */
	native static String perToXer(int type, byte[] per);

	/**
	 * Natively convert XER data to PER
	 * @param type Native enum representing type to convert
	 * @param xer XER data
	 * @return PER data (null if failed)
	 */
	native static byte[] xerToPer(int type, String xer);

	/**
	 * This prevents this class from being instantiated
	 */
	private Native() { }
}
