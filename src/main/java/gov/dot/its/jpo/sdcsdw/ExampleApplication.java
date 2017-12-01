package gov.dot.its.jpo.sdcsdw;

import javax.xml.bind.DatatypeConverter;

import gov.dot.its.jpo.sdcsdw.PerXerCodec.Asn1Type;

public class ExampleApplication
{
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
        
        type = PerXerCodec.getAsn1TypeByName(args[2]);
        
        if (type == null) {
            System.out.println("Usage: ");
            System.out.println("PerXerCodec (XER|PER) (16|64) TYPE DATA");
            System.exit(-1);
        }
        
        if (isXer) {
        
            per = PerXerCodec.xerToPer(type, xer);
            
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
                
                
                String xerRoundTrip = PerXerCodec.perToXer(type, per);
                
                if (xerRoundTrip == null) {
                    System.out.println("Could not round-trip XER");
                } else {
                    System.out.println("XER Round Trip:");
                    System.out.println(xerRoundTrip);
                }
            }
        } else {
            xer = PerXerCodec.perToXer(type, per);
            
            if (xer == null) {
                System.out.println("Could not decode PER");
            } else {
                System.out.println("XER: ");
                System.out.println(xer);
                
                byte[] perRoundTrip = PerXerCodec.xerToPer(type, xer);
                
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
}
