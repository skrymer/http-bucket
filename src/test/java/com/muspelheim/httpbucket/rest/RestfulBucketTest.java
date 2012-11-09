package com.muspelheim.httpbucket.rest;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.muspelheim.httpbucket.handler.RequestHandler;

@Test(groups=UNIT_TEST)
public class RestfulBucketTest {
	@Mock RequestHandler mockRequestHandler;
	@InjectMocks RestfulBucket sut;
	
	@BeforeMethod
	public void setUp(){
		sut = new RestfulBucket();	
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description="Should return a reponse ")
	public void testRestServlet_GETResponse() throws Exception{				
		HttpServletRequest restRequest = new MockHttpServletRequest("GET", null);
		String response = "<response></response>";
		
		given(mockRequestHandler.handleRequest(restRequest)).willReturn(response);
		
		sut.service(new MockHttpServletRequest("GET", null), new MockHttpServletResponse());
		
		verify(mockRequestHandler).handleRequest(restRequest);
	}
}
