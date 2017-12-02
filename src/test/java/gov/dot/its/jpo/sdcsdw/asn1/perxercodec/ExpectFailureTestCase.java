package gov.dot.its.jpo.sdcsdw.asn1.perxercodec;

public interface ExpectFailureTestCase extends TestCase
{
	Class<Throwable> getExpectedCatchType(); 
}
