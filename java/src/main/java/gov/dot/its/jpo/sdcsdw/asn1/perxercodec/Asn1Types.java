package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Known types for converting
 * @author andrew
 *
 */
public abstract class Asn1Types {
	/** Type for Advisory Situation Data messages
	 * 
	 */
	public static final Asn1Type AdvisorySituationDataType = new Asn1Type("AdvisorySituationData", Native.getAdvisorySituationDataType());
	/** Type for Service Request messages
	 * 
	 */
	public static final Asn1Type ServiceRequestType = new Asn1Type("ServiceRequest", Native.getServiceRequestType());
	/** Type for Service Response messages
	 * 
	 */
	public static final Asn1Type ServiceResponseType = new Asn1Type("ServiceResponse", Native.getServiceResponseType());
	/** Type for Data Request messages
	 * 
	 */
	public static final Asn1Type DataRequestType = new Asn1Type("DataRequest", Native.getDataRequestType());
	/** Type for Advisory Situation Data Distribution messages
	 * 
	 */
	public static final Asn1Type AdvisorySituationDataDistributionType = new Asn1Type("AdvisorySituationDataDistribution", Native.getAdvisorySituationDataDistributionType());
	/** Type for Data Acceptance messages
	 * 
	 */
	public static final Asn1Type DataAcceptanceType = new Asn1Type("DataAcceptance", Native.getDataAcceptanceType());
	/** Type for Data Receipt messages
	 * 
	 */
	public static final Asn1Type DataReceiptType = new Asn1Type("DataReceipt", Native.getDataReceiptType());

	/** Get the ASN.1 type object based on the type's name
	 * 
	 * @param name The type's name, in UpperCamelCase
	 * @return The ASN.1 type object, or null if no type with that name exists
	 */
	public static Asn1Type getAsn1TypeByName(String name)
	{
	    return typeMap.get(name);
	}
	
	/** Get an iterable of all supported types
	 * @return An iterable of all supported types
	 */
	public static Iterable<Asn1Type> getAllTypes()
	{
		return Arrays.asList(allTypes);
	}
	
	/**
	 * All types we know about
	 */
	private static final Asn1Type[] allTypes = {
			AdvisorySituationDataType,
			ServiceRequestType,
			ServiceResponseType,
			DataRequestType,
			AdvisorySituationDataDistributionType,
			DataAcceptanceType,
			DataReceiptType
	};
	
	/**
	 * A map between names and types
	 */
	private static final HashMap<String, Asn1Type> typeMap = new HashMap<>();
	
	
	static
	{
		for (Asn1Type type : allTypes) {
			typeMap.put(type.name, type);
		}	
	}
	
	/**
	 * Prevents this class from being instantiated
	 */
	private Asn1Types() { }
}
