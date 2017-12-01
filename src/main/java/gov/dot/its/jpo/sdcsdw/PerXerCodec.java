package gov.dot.its.jpo.sdcsdw;

import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class PerXerCodec
{
    static {
        System.loadLibrary("perxercodec");
    }
    
    public static final Asn1Type AdvisorySituationDataType = new Asn1Type(nativeGetAdvisorySituationDataType());
    
    public static final Asn1Type ServiceRequestType = new Asn1Type(nativeGetServiceRequestType());
    public static final Asn1Type ServiceResponseType = new Asn1Type(nativeGetServiceResponseType());
    public static final Asn1Type DataRequestType = new Asn1Type(nativeGetDataRequestType());
    public static final Asn1Type AdvisorySituationDataDistributionType = new Asn1Type(nativeGetAdvisorySituationDataDistributionType());
    public static final Asn1Type DataAcceptanceType = new Asn1Type(nativeGetDataAcceptanceType());
    public static final Asn1Type DataReceiptType = new Asn1Type(nativeGetDataReceiptType());
    
    static class Asn1Type
    {
        Asn1Type(int cInt)
        {
            this.cInt = cInt;
        }
        
        private final int cInt;
    }
    
    public static String perToXer(Asn1Type type, byte[] per)
    {
        return nativePerToXer(type.cInt, per);
    }
    
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
    
    public static void main(String[] args)
    {
        if (args.length < 4) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        String xer = null;
        byte[] per = null;
        boolean isXer;
        boolean is16;
        Asn1Type type;
        
        switch (args[0]) {
        case "XER":
            isXer = true;
            xer = args[1];
            break;
        case "PER":
            isXer = false;
            break;
        default:
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER-Hex|PER-64) DATA");
            System.exit(-1);
            return;
        }
        
        switch (args[1]) {
        case "16":
            is16 = true;
            break;
        case "64":
            is16 = false;
            break;
        default:
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER-Hex|PER-64) DATA");
            System.exit(-1);
            return;
        }
        
        if (!isXer) {
            if(is16) {
                System.out.println("TODO: base 16");
                System.exit(-1);
                return;
            } else {
                per = DatatypeConverter.parseBase64Binary(args[3]);
            }
        }
        
        type = getAsn1TypeByName(args[2]);
        
        if (type == null) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        if (isXer) {
        
            per = xerToPer(type, xer);
            
            if (per == null) {
                System.out.println("Could not decode XER");
            } else {
                System.out.println("PER:");
                if (is16) {
                    for(byte b : per) {
                        System.out.printf("0x%2x ", b);
                    }
                    System.out.println();
                } else {
                    System.out.println(DatatypeConverter.printBase64Binary(per));
                }
                
                
                String xerRoundTrip = perToXer(type, per);
                
                if (xerRoundTrip == null) {
                    System.out.println("Could not round-trip XER");
                } else {
                    System.out.println("XER Round Trip:");
                    System.out.println(xerRoundTrip);
                }
            }
        } else {
            xer = perToXer(type, per);
            
            if (xer == null) {
                System.out.println("Could not decode PER");
            } else {
                System.out.println("XER: ");
                System.out.println(xer);
                
                byte[] perRoundTrip = xerToPer(type, xer);
                
                if(perRoundTrip == null) {
                    System.out.println("Could not round-trip PER");
                } else {
                    System.out.println("PER Round Trip:");
                    if (is16) {
                        for(byte b : perRoundTrip) {
                            System.out.printf("0x%2x ", b);
                        }
                        System.out.println();
                    } else {
                        System.out.println(DatatypeConverter.printBase64Binary(perRoundTrip));
                    }
                }
            }
        }
    }
    
    public static byte[] xerToPer(Asn1Type type, String xer)
    {
        return nativeXerToPer(type.cInt, xer);
    }


    private native static int nativeGetAdvisorySituationDataType();
    
    private native static int nativeGetServiceRequestType();
    private native static int nativeGetServiceResponseType();
    private native static int nativeGetDataRequestType();
    private native static int nativeGetAdvisorySituationDataDistributionType();
    private native static int nativeGetDataAcceptanceType();
    private native static int nativeGetDataReceiptType();
    
    private native static String nativePerToXer(int type, byte[] per);
    private native static byte[] nativeXerToPer(int type, String xer);
}
