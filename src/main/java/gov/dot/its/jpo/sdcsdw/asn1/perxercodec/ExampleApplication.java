package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.CodecException;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.Base64PerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.HexPerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataFormatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.RawXerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerData;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataFormatter;
import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataUnformatter;

/**
 * Example main class to demonstrate the use of the codec
 * @author andrew
 *
 */
public class ExampleApplication
{
	/**
	 * Entry point
	 * 
	 * </p>
	 * 
	 * This example application takes either XER or PER data, and runs it through
	 * 	the codec, and then round-trips the output back through the codec again
	 * 
	 * </p>
	 * 
	 * Takes 4 arguments on the command line:
	 * * The first determines the format of the input data,
	 * * The second indicates if you want PER data to be inputted/outputted as hexadecmial strings or base64
	 * * The third indicates the name of the ASN.1 type to interpret the data as
	 * * The fourth is the data itself
	 * @param args
	 */
    public static void main(String[] args)
    {
        if (args.length < 4) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        String xer = null;
        String per = null;
        XerDataFormatter<String, XerData<String>> xerFormatter = RawXerData::new;
        XerDataUnformatter<String, XerData<String>> xerUnformatter = RawXerData::new;
        PerDataFormatter<String, PerData<String>> perFormatter;
        PerDataUnformatter<String, PerData<String>> perUnformatter;
        boolean isXer;
        Asn1Type type;
        
        switch (args[0]) {
        case "XER":
            isXer = true;
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
            perFormatter = HexPerData::new;
            perUnformatter = HexPerData::new;
            break;
        case "64":
            perFormatter = Base64PerData::new;
            perUnformatter = Base64PerData::new;
            break;
        default:
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER-Hex|PER-64) DATA");
            System.exit(-1);
            return;
        }
        
        type = Asn1Types.getAsn1TypeByName(args[2]);
        
        if (type == null) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        if (isXer) {
        	xer = args[3];
        	
        	try {
        		per = PerXerCodec.xerToPer(type, xer, xerUnformatter, perFormatter);
        		System.out.println("PER:");
                System.out.println(per);
        	} catch (CodecException ex) {
        		System.out.println("Could not decode XER: " + ex.getMessage());
        		System.exit(-1);
        	}
           
        	try {
        		String xerRoundTrip = PerXerCodec.perToXer(type, per, perUnformatter, xerFormatter);
        		System.out.println("XER Round Trip:");
                System.out.println(xerRoundTrip);
        	} catch (CodecException ex) {
        		System.out.println("Could not round-trip XER: " + ex.getMessage());
        	}
        } else {
        	per = args[3];
        	try {
        		xer = PerXerCodec.perToXer(type, per, perUnformatter, xerFormatter);
        		System.out.println("XER: ");
                System.out.println(xer);
        	} catch (CodecException ex) {
        		System.out.println("Could not decode PER: " + ex.getMessage());
        	}
        	
          
        	try {
        		String perRoundTrip = PerXerCodec.xerToPer(type, xer, xerUnformatter, perFormatter);
        		System.out.println("PER Round Trip:");
                System.out.println(perRoundTrip);
        	} catch (CodecException ex) {
        		System.out.println("Could not round-trip PER: " + ex.getMessage());
        	}
        }
    }
}
