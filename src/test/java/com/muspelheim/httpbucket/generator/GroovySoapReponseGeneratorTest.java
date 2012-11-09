package com.muspelheim.httpbucket.generator;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static com.muspelheim.util.matcher.XMLMatcher.isSimilarTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.core.io.FileSystemResource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.muspelheim.httpbucket.generator.impl.GroovySoapResponseGenerator;
import com.muspelheim.httpbucket.soap.util.SoapUtils;

@Test(groups=UNIT_TEST)
public class GroovySoapReponseGeneratorTest {
  private SoapUtils soapUtils = new SoapUtils();
	
	private GroovySoapResponseGenerator sut;
	
	@BeforeMethod
	public void setUp() throws Exception{
		soapUtils = new SoapUtils();
		
		sut = new GroovySoapResponseGenerator(new FileSystemResource("src/test/resources/com/muspelheim/httpbucket/generator/"));
		sut.setSoapUtils(soapUtils);
	}
	
	@Test(description="Should return a response selected by the given Groovy script", 
			  dataProvider="request2responseMappings")
	public void testGroovyReponseGenerator_generateReponse(String requestPayload, String expectedResponsePayload) throws Exception{
		String scriptName = "generateXmlResponse.groovy";

		String actualResponsePayload = sut.generateSoapResponse(scriptName, soapUtils.createSoapMessage(requestPayload));
				
		assertThat(actualResponsePayload, isSimilarTo(expectedResponsePayload));
	}
	
//----------------
// Helper methods 
//----------------
	
	@DataProvider(name="request2responseMappings")
  public Object[][] createRequest2ResponseMapping(){
	  return new Object[][]{
	      {"<request><data><responseToReturn>response.xml</responseToReturn></data></request>", "<response>response</response>"},
    		{"<request><data><responseToReturn>response2.xml</responseToReturn></data></request>", "<response>response2</response>"}
  	};
  }
}
