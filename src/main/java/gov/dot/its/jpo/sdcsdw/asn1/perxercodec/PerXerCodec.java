package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

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
    /** Result of guessing which ASN1 type is present in data 
     * 
     * @author amm30955
     *
     * @param <T>
     */
    public static class TypeGuessResult<T>
    {
        /** Create a successful result
         * 
         * @param type Type found
         * @param data Data parsed
         */
        public TypeGuessResult(Asn1Type type, T data)
        {
            this.type = type;
            this.data = data;
        }
        
        /** Create a failed result
         * 
         */
        public TypeGuessResult()
        {
            this.type = null;
            this.data = null;
        }
        
        /** Test if the result is success
         * 
         * @return True if a type was found
         */
        public boolean isSuccesful()
        {
            return type != null && data != null;
        }
        
        /** Get the type found
         * 
         * @return The type found, or null if unsuccessful
         */
        public Asn1Type getType()
        {
            return type;
        }
        
        /** Get the data parsed
         * 
         * @return The data parsed, or null if unsucessful
         */
        public T getData()
        {
            return data;
        }
        
        /** Type found */
        private final Asn1Type type;
        
        /** Data parsed */
        private final T data;
    }
    
    /** Convert PER encoded data into XER encoded data 
     * 
     * @param <PER> Input type representing PER data
     * @param <XER> Output type representing XER data
     * @param <PerT> Wrapper around PER
     * @param <XerT> Wrapper around XER
     * @param type The type the PER encoded data contains
     * @param per The PER encoded data
     * @param perUnformatter A hint as to how the PER data is represented in memory
     * @param xerFormatter A hint as to how to represent output XER in memory
     * @return The XER encoded data
     * @throws CodecFailedException If the generated ASN.1 code could not handle the given data
     * @throws FormattingFailedException If the desired XER representation could not be built
     * @throws UnformattingFailedException If the PER data could not be interpreted
     */
    public static <PER, PerT extends PerData<PER>, XER, XerT extends XerData<XER>>
    XER perToXer(Asn1Type type,
    		     PER per,
    		     PerDataUnformatter<PER, PerT> perUnformatter,
    		     XerDataFormatter<XER, XerT> xerFormatter)
        throws CodecFailedException, FormattingFailedException, UnformattingFailedException
    {
    	    PerT rawPer = perUnformatter.unformatPerData(per);
    	
        String rawXer = Native.perToXer(type.cInt, rawPer.getPerData());
        if (rawXer == null) {
            throw new CodecFailedException("Could not convert PER data to XER: " + per);
        } else {
            return xerFormatter.formatXerData(rawXer).getFormattedXerData();
        }
    }
    
    /** Convert PER encoded data into XER encoded data, while guessing what type of data is present
     * 
     * @param <PER> Input type representing PER data
     * @param <XER> Output type representing XER data
     * @param <PerT> Wrapper around PER
     * @param <XerT> Wrapper around XER
     * @param types The types the PER encoded data could possibly contain
     * @param per The PER encoded data
     * @param perUnformatter A hint as to how the PER data is represented in memory
     * @param xerFormatter A hint as to how to represent output XER in memory
     * @return The result of guessing
     * @throws UnformattingFailedException If the desired XER representation could not be built
     * @throws FormattingFailedException If the PER data could not be interpreted
     */
    public static <PER, PerT extends PerData<PER>, XER, XerT extends XerData<XER>>
    TypeGuessResult<XER> guessPerToXer(Iterable<Asn1Type> types, PER per, PerDataUnformatter<PER, PerT> perUnformatter, XerDataFormatter<XER, XerT> xerFormatter)
        throws UnformattingFailedException, FormattingFailedException
    {
        PerT rawPer = perUnformatter.unformatPerData(per);
        
        for(Asn1Type type : types) {
            String rawXer = Native.perToXer(type.cInt, rawPer.getPerData());
            if (rawXer != null) {
                return new TypeGuessResult<XER>(type, xerFormatter.formatXerData(rawXer).getFormattedXerData());
            }
        }
        
        return new TypeGuessResult<XER>();
    }
    
    /** Convert XER encoded data into PER encoded data 
     * 
     * @param <XER> Input type representing XER data
     * @param <PER> Output type representing PER data
     * @param <XerT> Wrapper around XER
     * @param <PerT> Wrapper around PER
     * @param type The ASN.1 type the XER encoded data contains
     * @param xer The XER encoded data
     * @param xerUnformatter A hint as to how the XER data is represented in memory
     * @param perFormatter A hint as to how to represent output PER in memory
     * @return The PER encoded data
     * @throws UnformattingFailedException If the XER data could not be interpreted 
     * @throws CodecFailedException If the generated ASN.1 code could not handle the given data
     * @throws FormattingFailedException If the desired PER representation could not be built
     */
    public static <XER, XerT extends XerData<XER>, PER, PerT extends PerData<PER>>
    PER xerToPer(Asn1Type type, 
    			 XER xer, 
    			 XerDataUnformatter<XER, XerT> xerUnformatter, 
    			 PerDataFormatter<PER, PerT> perFormatter)
    	throws UnformattingFailedException, CodecFailedException, FormattingFailedException
    {
    	
    	XerT rawXer = xerUnformatter.unformatXerData(xer);
    	
        byte[] rawPer = Native.xerToPer(type.cInt, rawXer.getXerData());
        if (rawPer == null) {
            throw new CodecFailedException("Could not convert XER data to PER: " + xer);
        } else {
            return perFormatter.formatPerData(rawPer).getFormattedPerData();
        }
    }
    
    /** Convert XER encoded data into PER encoded data, while guessing what type of data is present
     * 
     * @param <XER> Input type representing XER data
     * @param <PER> Output type representing PER data
     * @param <XerT> Wrapper around XER
     * @param <PerT> Wrapper around PER
     * @param types The types the XER encoded data could possibly contain
     * @param xer The XER encoded data
     * @param xerUnformatter A hint as to how the XER data is represented in memory
     * @param perFormatter A hint as to how to represent output PER in memory
     * @return The result of guessing
     * @throws UnformattingFailedException If the desired PER representation could not be built
     * @throws FormattingFailedException If the XER data could not be interpreted
     */
    public static <XER, XerT extends XerData<XER>, PER, PerT extends PerData<PER>>
    TypeGuessResult<PER> guessXerToPer(Iterable<Asn1Type> types, XER xer, XerDataUnformatter<XER, XerT> xerUnformatter, PerDataFormatter<PER, PerT> perFormatter)
        throws UnformattingFailedException, FormattingFailedException
    {
        XerT rawXer = xerUnformatter.unformatXerData(xer);
        
        for(Asn1Type type : types) {
            byte[] rawPer = Native.xerToPer(type.cInt, rawXer.getXerData());
            if (rawPer != null) {
                return new TypeGuessResult<PER>(type, perFormatter.formatPerData(rawPer).getFormattedPerData());
            }
        }
        
        return new TypeGuessResult<PER>();
    }
}
