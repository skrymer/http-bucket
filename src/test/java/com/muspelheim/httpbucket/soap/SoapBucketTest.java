package com.muspelheim.httpbucket.soap;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static org.mockito.BDDMockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.muspelheim.httpbucket.handler.RequestHandler;

@Test(groups=UNIT_TEST)
public class SoapBucketTest {
	@Mock RequestHandler mockRequestHandler;
	@Mock MessageContext mockMessageContext;
	@Mock SoapMessage mockRequest;
	@Mock SoapMessage mockResponse;
	
	@InjectMocks SoapBucket sut;
	
	@BeforeMethod
	public void configureMocks(){
		sut = new SoapBucket();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description="GIVEN a SOAP request Should set generated response on the MessageContext")
	public void testSoapBucket_invoke() throws Exception{
		//given
		given(mockMessageContext.getRequest()).willReturn(mockRequest);
		given(mockRequestHandler.handleRequest(mockRequest)).willReturn(mockResponse);
		
		//when
		sut.invoke(mockMessageContext);
		
		//then
		verify(mockMessageContext).setResponse(mockResponse);
	}
}
