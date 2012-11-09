package com.muspelheim.httpbucket.util;

import static com.muspelheim.util.TestGroups.*;
import static org.testng.Assert.*;
import javax.xml.namespace.QName;

import org.springframework.ws.soap.SoapMessage;
import org.testng.annotations.Test;

import com.muspelheim.httpbucket.soap.util.SoapUtils;

@Test(groups=UNIT_TEST)
public class SoapUtilGetPayloadQNameTest {
	
	@Test(description="Should return the QName for the given SoapMessages payload")
	public void testSoapUtil_getPayloadQName(){
		SoapUtils sut = new SoapUtils();
		SoapMessage message = sut.createSoapMessage("<element xmlns=\"http//:muspelheim.com\"><child>value</child></element>");
		
		//Invoke sut
		QName actualQName = sut.getPayloadQName(message);
	
		assertEquals(actualQName, new QName("http//:muspelheim.com", "element"));
	}
}
