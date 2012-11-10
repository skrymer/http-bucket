package com.muspelheim.httpbucket.rest;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@Mock RequestHandler<String, HttpServletRequest> mockRequestHandler;
	@InjectMocks RestfulBucket sut;
	
	@BeforeMethod
	public void setUp(){
		sut = new RestfulBucket();	
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description="Should return a reponse")
	public void testRestServlet_GETResponse() throws Exception{				
		HttpServletRequest restRequest = new MockHttpServletRequest("GET", null);
		MockHttpServletResponse httpResponse = new MockHttpServletResponse();
		
		String exptectedResponse = "<response></response>";
		
		given(mockRequestHandler.handleRequest(restRequest)).willReturn(exptectedResponse);
		
		sut.service(restRequest, httpResponse);

		assertEquals(httpResponse.getContentAsString(), exptectedResponse);
		
		verify(mockRequestHandler).handleRequest(restRequest);
	}
}
