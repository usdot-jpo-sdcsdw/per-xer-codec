/**
 * This package provides a codec for translating ASN.1 J2735 (+ SEMI test bed extensions) between UPER and XER
 * 
 * </p>
 * 
 * Supports the following types:
 * <ul>
 * <li>ServiceRequest</li>
 * <li>ServiceResponse</li>
 * <li>DataRequest</li>
 * <li>AdvisorySituationDataDistribution</li>
 * <li>DataAcceptance</li>
 * <li>DataReceipt</li>
 * <li>AdvisorySituationData</li>
 * </ul>
 * 
 * Can understand PER data in the following formats:
 * <ul>
 * <li>Byte Array</li>
 * <li>Hexadecimal String</li>
 * <li>Base 64 String</li>
 * </ul>
 * 
 * Can understand XER data in the following formatS:
 * <ul>
 * <li>XML String</li>
 * <li>Javax XML Document</li>
 * </ul>
 * 
 * The basic use-case of this package is calling
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#perToXer(Asn1Type, Object, gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter, XerDataFormatter) perToXer}
 * or {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#xerToPer(Asn1Type, Object, gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataUnformatter, PerDataFormatter) xerToPer}.
 * 
 * </p>
 * 
 * To call these methods, you will need to specify what type you are converting, and how the input and output data
 * are/should be represented. Specifying the type is done by using the fields in
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.Asn1Types}.
 * xerToPer and perToXer will operate directly on the data you need to convert, but require information on how to
 * interpret that data as PER or XER. This is where the interfaces
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataFormatter PerDataFormatter},
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter PerDataUnformatter},
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataFormatter XerDataFormatter},
 * and {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataFormatter XerDataUnformatter} come in.
 * 
 * 
 * 
 * </p>
 * 
 * Classes in this package will generally throw exceptions that descend from
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.CodecException}.
 * 
 *  </p>
 *  
 *  Example
 *  <pre>
 *  <code>
 *  
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.PerXerCodec;
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.per.Base64PerData;
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.xer.XmlXerData;
 *   import static gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.Asn1Types;
 *   
 *   import org.w3c.dom.Document;
 *   
 *   // ...
 *   
 *   String perDataAsBase64 = //...
 *   
 *   try {
 *   	Document xerDataAsDocument = PerXerCodec.perToXer(ServiceRequestType, perDataAsBase64 Base64PerData::unformatter, XmlXerData::formatter);
 *   } catch (CodecException ex) {
 *   	// Deal with a failed conversion here
 *   }
 *  </code>
 *  </pre>
 */
package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;
