/**
 * This package provides a codec for translating ASN.1 J2735 (+ SEMI test bed extensions) between UPER and XER
 * 
 * <p>
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
 * Can understand XER data in the following formats:
 * <ul>
 * <li>XML String</li>
 * <li>Javax XML Document</li>
 * </ul>
 * 
 * Classes in this package will generally throw exceptions that descend from
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.exception.CodecException}.
 * 
 * <p>
 *  
 * The basic use-case of this package is calling
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#perToXer(Asn1Type, Object, gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter, XerDataFormatter) perToXer}
 * or {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.PerXerCodec#xerToPer(Asn1Type, Object, gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataUnformatter, PerDataFormatter) xerToPer}.
 * 
 * <p>
 * 
 * To call these methods, you will need to specify what type you are converting, and how the input and output data
 * are/should be represented. Specifying the type is done by using the fields in
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.Asn1Types}. You can also query for a type by name (UpperCamelCase) by
 * calling {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.Asn1Types#getAsn1TypeByName(String)}. 
 * xerToPer and perToXer will operate directly on the data you need to convert, but require information on how to
 * interpret that data as PER or XER. This is accomplished by passing an instance of
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataFormatter PerDataFormatter},
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.per.PerDataUnformatter PerDataUnformatter},
 * {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataFormatter XerDataFormatter},
 * or {@link gov.dot.its.jpo.sdcsdw.asn1.perxercodec.xer.XerDataFormatter XerDataUnformatter}.
 * Instances of XerData and PerData provided by this library each provide a pair of static fields, one formatter and one
 * unformatter which are used by the codec methods to obtain the exact data to pass
 * to the underlying generated code, and then to use the result. 
 * 
 *  <p>
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
 *   	Document xerDataAsDocument = PerXerCodec.perToXer(ServiceRequestType, perDataAsBase64, Base64PerData.unformatter, XmlXerData.formatter);
 *   } catch (CodecException ex) {
 *   	// Deal with a failed conversion here
 *   }
 *  </code>
 *  </pre>
 *  
 *  In addition to the library-provided formats, a user-defined format for some type (Henceforce referred to as
 *  "MyType") can be created by creating a class (Henceforth referred to as "MyClass") which obeys the following
 *  contract:
 *  
 *  <p>For a PER format:
 *  
 *  <ul>
 *  <li>Implement <code>PerData{@literal <MyType>}</code>
 *  	<p>getPerData should return the actual bytes containing PER data.
 *  	<p>getFormattedPerData should return an instance of MyType
 *  </li>
 *  <li>Provide a public, static, final member named "formatter", with type <code>{@literal PerDataFormatter<MyType, MyClass>}</code>.
 *      <p>Because this is a functional interface, it is possible to have this member be a lambda which takes MyType
 *      and returns MyClass. If MyClass has a constructor which takes a MyType, this member can simply be a method
 *      reference to the constructor.
 *  </li>
 *  <li>Provide a public, static, final member named "unformatter", with type <code>{@literal PerDataUnformatter<MyType, MyClass>}</code>.
 *      <p>Because this is a functional interface, it is possible to have this member be a lambda which takes a <code>byte[]</code>
 *      and returns MyClass. If MyClass has a constructor which takes a <code>byte[]</code>, this member can simply be a method
 *      reference to the constructor.
 *  </li>
 *  <li>Override <code>equals()</code> to return true if the other object is an instance of PerData, and the calls to getPerData() return the same bytes, and false otherwise</li>
 *  </ul>
 *  
 *  For a XER format:
 *  
 *  <ul>
 *  <li>Implement <code>{@literal XerData<MyType>}</code>
 *  	<p>getXerData should return a string containing the actual XML XER data.
 *  	<p>getFormattedXerData should return an instance of MyType
 *  </li>
 *  <li>Provide a public, static, final member named "formatter", with type <code>{@literal XerDataFormatter<MyType, MyClass>}</code>.
 *      <p>Because this is a functional interface, it is possible to have this member be a lambda which takes MyType
 *      and returns MyClass. If MyClass has a constructor which takes a MyType, this member can simply be a method
 *      reference to the constructor.
 *  </li>
 *  <li>Provide a public, static, final member named "unformatter", with type <code>{@literal XerDataUnformatter<MyType, MyClass>}</code>.
 *      <p>Because this is a functional interface, it is possible to have this member be a lambda which takes a <code>String</code>
 *      and returns MyClass. If MyClass has a constructor which takes a <code>String</code>, this member can simply be a method
 *      reference to the constructor.
 *  </li>
 *  <li>Override <code>equals()</code> to return true if the other object is an instance of XerData, and the calls to getXerData() return the same xml, modulo whitespace</li>
 *  </ul>
 */
package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;
