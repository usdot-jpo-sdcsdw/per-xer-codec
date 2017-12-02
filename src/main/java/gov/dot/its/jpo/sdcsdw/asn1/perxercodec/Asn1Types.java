package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

/**
 * Known types for converting
 * @author andrew
 *
 */
public abstract class Asn1Types {
	/** Type for Advisory Situation Data messages
	 * 
	 */
	public static final Asn1Type AdvisorySituationDataType = new Asn1Type(Native.getAdvisorySituationDataType());
	/** Type for Service Request messages
	 * 
	 */
	public static final Asn1Type ServiceRequestType = new Asn1Type(Native.getServiceRequestType());
	/** Type for Service Response messages
	 * 
	 */
	public static final Asn1Type ServiceResponseType = new Asn1Type(Native.getServiceResponseType());
	/** Type for Data Request messages
	 * 
	 */
	public static final Asn1Type DataRequestType = new Asn1Type(Native.getDataRequestType());
	/** Type for Advisory Situation Data Distribution messages
	 * 
	 */
	public static final Asn1Type AdvisorySituationDataDistributionType = new Asn1Type(Native.getAdvisorySituationDataDistributionType());
	/** Type for Data Acceptance messages
	 * 
	 */
	public static final Asn1Type DataAcceptanceType = new Asn1Type(Native.getDataAcceptanceType());
	/** Type for Data Receipt messages
	 * 
	 */
	public static final Asn1Type DataReceiptType = new Asn1Type(Native.getDataReceiptType());

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
	        return Asn1Types.ServiceRequestType;
	    case "ServiceResponse":
	        return Asn1Types.ServiceResponseType;
	    case "DataRequest":
	        return Asn1Types.DataRequestType;
	    case "AdvisorySituationDataDistribution":
	        return Asn1Types.AdvisorySituationDataDistributionType;
	    case "DataAcceptance":
	        return Asn1Types.DataAcceptanceType;
	    case "DataReceipt":
	        return Asn1Types.DataReceiptType;
	    default:
	        return null;
	    }
	}
	
	/**
	 * Prevents this class from being instantiated
	 */
	private Asn1Types() { }
}
