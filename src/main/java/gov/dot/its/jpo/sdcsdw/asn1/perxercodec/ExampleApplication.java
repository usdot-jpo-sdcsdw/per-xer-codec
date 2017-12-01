package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

import java.util.function.Function;

import javax.xml.bind.DatatypeConverter;

import gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec.Asn1Type;

public class ExampleApplication
{
    public static void main(String[] args)
    {
        if (args.length < 4) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        XerData xer = null;
        PerData per = null;
        Function<String, XerData> xerBuilder = RawXerData::new;
        Function<byte[], PerData> perBuilder;
        boolean isXer;
        boolean is16;
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
            is16 = true;
            perBuilder = HexPerData::new;
            break;
        case "64":
            is16 = false;
            perBuilder = Base64PerData::new;
            break;
        default:
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER-Hex|PER-64) DATA");
            System.exit(-1);
            return;
        }
        
        type = PerXerCodec.getAsn1TypeByName(args[2]);
        
        if (type == null) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        if (!isXer) {
            if(is16) {
                per = new HexPerData(args[3]);
            } else {
                per = new Base64PerData(args[3]);
            }
        } else {
            xer = new RawXerData(args[3]);
        }
        
        if (isXer) {
            per = PerXerCodec.xerToPer(type, xer, perBuilder);
            
            if (per == null) {
                System.out.println("Could not decode XER");
            } else {
                System.out.println("PER:");
                System.out.println(per);
                
                
                XerData xerRoundTrip = PerXerCodec.perToXer(type, per, xerBuilder);
                
                if (xerRoundTrip == null) {
                    System.out.println("Could not round-trip XER");
                } else {
                    System.out.println("XER Round Trip:");
                    System.out.println(xerRoundTrip);
                }
            }
        } else {
            xer = PerXerCodec.perToXer(type, per, xerBuilder);
            
            if (xer == null) {
                System.out.println("Could not decode PER");
            } else {
                System.out.println("XER: ");
                System.out.println(xer);
                
                PerData perRoundTrip = PerXerCodec.xerToPer(type, xer, perBuilder);
                
                if(perRoundTrip == null) {
                    System.out.println("Could not round-trip PER");
                } else {
                    System.out.println("PER Round Trip:");
                    System.out.println(perRoundTrip);
                }
            }
        }
    }
}
