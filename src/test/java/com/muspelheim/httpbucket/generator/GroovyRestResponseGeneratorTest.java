package com.muspelheim.httpbucket.generator;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static org.testng.Assert.assertEquals;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.muspelheim.httpbucket.generator.impl.GroovyRestResponseGenerator;

@Test(groups=UNIT_TEST)
public class GroovyRestResponseGeneratorTest {
	private GroovyRestResponseGenerator sut;
		
	@BeforeMethod
	public void setUp(){
		FileSystemResource script = new FileSystemResource("src/test/resources/com/muspelheim/httpbucket/generator/generateHttpResponseContent.groovy");
		sut = new GroovyRestResponseGenerator(script, null);
	}
	
	@Test(description="Should set the content on the HttpResponse thats associated with given request path", 
			  dataProvider="pathToResponseContentMapping")
	public void testGroovyRestResponseGenerator_GETResponse(String path, String expectedResponseContent) throws Exception{				
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		mockHttpServletRequest.setMethod("GET");
		mockHttpServletRequest.setContextPath(path);
		
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
				
		sut.setHttpResponseContent(mockHttpServletRequest, mockHttpServletResponse);
		
		assertEquals(mockHttpServletResponse.getContentAsString(), expectedResponseContent, "Response should contain: " + expectedResponseContent);
	}
	
	//TODO test with query parameters
	
//---------------
// Helpers
//---------------
	
	@DataProvider(name="pathToResponseContentMapping")
	public Object[][] getPathToResponseMapping(){
		return new Object[][]{
			{"/opel/vectra", "opel vectra info"},
			{"/sony/tv/md350","Sony televesion model md350 info"},
			{"/australia/queensland/brisbane", "Info on Brisbane"}
		};
	}
}
