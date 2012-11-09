package com.muspelheim.httpbucket.handler;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static com.muspelheim.util.matcher.XMLMatcher.isSimilarTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;

import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ws.soap.SoapMessage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.muspelheim.httpbucket.generator.SoapResponseGenerator;
import com.muspelheim.httpbucket.handler.impl.SoapRequestHandlerImpl;
import com.muspelheim.httpbucket.mapping.Request2ScriptMapper;
import com.muspelheim.httpbucket.mapping.SoapRequest2ScriptMapping;
import com.muspelheim.httpbucket.soap.util.SoapUtils;

@Test(groups=UNIT_TEST)
public class SoapRequestHandlerTest {
  SoapUtils soapUtils = new SoapUtils();

	@Mock Request2ScriptMapper mockRequest2ScriptMapper;
	@Mock SoapResponseGenerator mockSoapResponseGenerator;
	@Mock SoapUtils mockSoapUtils;
	@Mock SoapRequest2ScriptMapping mockMapping;
	@InjectMocks SoapRequestHandlerImpl sut;
	
	@BeforeMethod
	public void setUp(){
		sut = new SoapRequestHandlerImpl();
		soapUtils = new SoapUtils();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description="Should return the response returned by the script associated with the given request when a mapping with the same QName as the request is found")
	public void testSoapRequestHandler_handleRequest() throws Exception{
		String expectedResponse 	= "<response></response>";
		
		List<SoapRequest2ScriptMapping> mappings = new LinkedList<SoapRequest2ScriptMapping>();
		mappings.add(new SoapRequest2ScriptMapping(new QName("namespace", "element"), null));
		
		//given
		given(mockRequest2ScriptMapper.getMappings()).willReturn(mappings);
		given(mockSoapResponseGenerator.generateSoapResponse(any(String.class), isA(SoapMessage.class))).willReturn(expectedResponse);
		given(mockSoapUtils.createSoapMessage(expectedResponse)).willReturn(soapUtils.createSoapMessage(expectedResponse));
		given(mockSoapUtils.getPayloadQName(isA(SoapMessage.class))).willReturn(new QName("namespace", "element"));
		
		//when
		SoapMessage soapRequest 	= soapUtils.createSoapMessage("<element xmlns=\"namespace\"><child></child></element>");
		SoapMessage response 			= sut.handleRequest(soapRequest);
				
		//then
		assertThat(expectedResponse, isSimilarTo(soapUtils.getPayloadAsString(response)));
	}
	
	//TODO should throw CantHandleRequestException if there is no matching mapping
}
