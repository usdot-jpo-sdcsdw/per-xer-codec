package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

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
            System.loadLibrary(nativeLibraryName);
        } catch (UnsatisfiedLinkError ex) {
            throw new RuntimeException( "Could not find native shared library for PER XER Codec on the following path: "
                                      + System.getProperty("java.library.path")
                                      + ", expecting a file named "
                                      + System.mapLibraryName(nativeLibraryName), ex);
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
