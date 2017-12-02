/**
 * This package provides a codec for translating ASN.1 J2735 (+ SEMI test bed extensions) between UPER and XER
 * 
 * </p>
 * 
 * Currently supports the following types:
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
 * The basic use-case of this package is calling {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#perToXer(Asn1Type, PerData, XerDataBuilder) perToXer}
 * or {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#xerToPer(Asn1Type, XerData, PerDataBuilder) xerToPer}.
 * 
 * </p>
 * 
 * To call these methods, you will need to select a {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec.Asn1Type Asn1Type} (e.g.
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#ServiceRequestType ServiceRequestType}), A {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerData PerData} or
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.XerData XerData} implementation,
 * (e.g. {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.Base64PerData Base64PerData}), and a
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.XerDataBuilder XerDataBuilder} or
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerDataBuilder PerDataBuilder} (These are functional interfaces, so a
 * reference to {@code *::new} for your XerData or PerData instance of choice will usually suffice).
 * 
 * </p>
 * 
 * Classes in this package will generally throw exceptions that descend from
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.CodecException}.
 * 
 *  </p>
 *  
 *  Example
 *  <pre>
 *  <code>
 *  
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.PerXerCodec;
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.XerData;
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.PerData;
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.Base64PerData;
 *   import gov.dot.ids.jpo.sdcsdw.asn1.perxercodec.XmlXerData;
 *   
 *   import org.w3c.dom.Document;
 *   
 *   // ...
 *  
 *   // Construct PerData from a string containing base64
 *   Base64PerData myPerData = new Base64PerData(myBase64String);
 *   
 *   // Select the service request type
 *   Asn1Type myAsn1Type = PerXerCodec.SericeRequestType;
 *   
 *   // Choose to build a javax XML document from the result
 *   XerDataBuilder<XmlXerData> myXerDataBuilder = XmlXerData::new;
 *   
 *   try {
 *   	// Pass the above to the codec's static method
 *   	XmlXerData myXerData = PerXerCodec.perToXer(myAsn1Type, myPerData, myXerDataBuilder);
 *   
 *   	// Extract the document
 *   	Document myXerDocument = myXerData.getDocumentXerData();
 *   } catch (CodecException ex) {
 *   	// Deal with a failed conversion 
 *   	// ...
 *   }
 *  </code>
 *  </pre>
 */
package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

