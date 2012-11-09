package com.muspelheim.httpbucket.mapper;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import javax.xml.namespace.QName;

import org.testng.annotations.Test;

import com.muspelheim.httpbucket.mapping.SoapRequest2ScriptMapping;

@Test(groups=UNIT_TEST)
public class SoapRequest2ScriptMappingEqualityTest {

	@Test(description="Should return true if the mappings contains the same QName and script-name")
	public void testSoapRequest2ScriptMapping_equal(){
		SoapRequest2ScriptMapping mapping1 = new SoapRequest2ScriptMapping(new QName("namespace", "localPart"), "script");
		SoapRequest2ScriptMapping mapping2 = new SoapRequest2ScriptMapping(new QName("namespace", "localPart"), "script");
		
		assertEquals(mapping1, mapping2);
	}
	
	@Test(description="Should return false if the mappings contains different QNames")
	public void testSoapRequest2ScriptMapping_namespaceNotEqual(){
		SoapRequest2ScriptMapping mapping1 = new SoapRequest2ScriptMapping(new QName("namespace1", "localpart1"), "script");
		SoapRequest2ScriptMapping mapping2 = new SoapRequest2ScriptMapping(new QName("namespace2", "localpart2"), "script");
		
		assertNotEquals(mapping1, mapping2);		
	}

	@Test(description="Should return false if the mappings contains different script paths")
	public void testSoapRequest2ScriptMapping_scriptPathNotEqual(){
		SoapRequest2ScriptMapping mapping1 = new SoapRequest2ScriptMapping(new QName("namespace", "localpart1"), "script1");
		SoapRequest2ScriptMapping mapping2 = new SoapRequest2ScriptMapping(new QName("namespace", "localpart1"), "script2");
		
		assertNotEquals(mapping1, mapping2);		
	}
}
