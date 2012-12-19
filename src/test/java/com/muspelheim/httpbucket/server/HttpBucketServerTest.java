package com.muspelheim.httpbucket.server;

import static com.muspelheim.util.TestGroups.INTEGRATION_TEST;
import static org.testng.Assert.assertEquals;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.muspelheim.httpbucket.soap.util.SoapUtils;

/**
 * @author Sonni Nielsen
 */
@Test(groups=INTEGRATION_TEST)
@ContextConfiguration(locations="classpath:http-bucket-server-context.xml")
public class HttpBucketServerTest extends AbstractTestNGSpringContextTests {
  private static final SoapUtils SOAP_UTILS = new SoapUtils();
	
  private WebServiceTemplate template;
  
  @Autowired HttpBucketServer sut;

  @BeforeClass
  public void startHttpBucketServer() throws Exception{
    template = new WebServiceTemplate();
    template.setDefaultUri("http://localhost:9080/soap");        
    RestAssured.baseURI = "http://localhost:9080/rest";
    sut.start();
  }

  @AfterClass
  public void stopHttpBucketServer() throws Exception{
    sut.stop();
  }
  
  @Test(description="GIVEN a file containing qnames 2 script mappings \n" + 
                    "WHEN a SOAP request is send \n" + 
                    "THEN the script associated with the request returns a response")
  public void testServer_getSoapResponse() throws Exception{
  	//Create SOAP message and send
	Source source = SOAP_UTILS.createSoapMessage("<request xmlns=\"http://muspelheim.com\"><data><responseToReturn>response.xml</responseToReturn></data></request>").getPayloadSource();
    DOMResult result = new DOMResult();
    template.sendSourceAndReceiveToResult(source, result);
        
    //Check that the correct response was returned
    DOMSource resultAsSource = new DOMSource(result.getNode());
    String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><response>response</response>";
    assertEquals(SOAP_UTILS.getSourceAsString(resultAsSource), expectedXml);
  }

  @Test(description="GIVEN a file containing url 2 script mappings \n" +
  					"WHEN a REST request is send \n" + 
      				"THEN the script associated with the request returns a response")
  public void testServer_getRestResponse() throws Exception{
  	//Set groovy script
  	//TODO implement REST
  	//System.setProperty("groovy.script.location", "src/test/resources/scripts/generateHttpResponseContent.groovy");
  	
  	//expect().body("engine.volume", hasItem("v6")).
  		//when().get("/opel/vectra");
  }
}
