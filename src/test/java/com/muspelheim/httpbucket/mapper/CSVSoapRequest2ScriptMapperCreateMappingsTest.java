package com.muspelheim.httpbucket.mapper;

import static com.muspelheim.util.TestGroups.UNIT_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import au.com.bytecode.opencsv.CSVReader;

import com.muspelheim.httpbucket.mapping.SoapRequest2ScriptMapping;
import com.muspelheim.httpbucket.mapping.exception.ReadingCsvFileException;
import com.muspelheim.httpbucket.mapping.impl.CSVSoapRequest2ScriptMapper;

@Test(groups=UNIT_TEST)
public class CSVSoapRequest2ScriptMapperCreateMappingsTest {
	
	@Mock CSVReader mockCsvReader;
	@InjectMocks CSVSoapRequest2ScriptMapper sut;

	@BeforeMethod
	public void setUp(){
		sut = new CSVSoapRequest2ScriptMapper();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description="Should generate a list of SoapRequest2ScriptMappings based on the given CSV file")
	public void testCSVSoapRequest2ScriptMapper_createMappings() throws Exception {
		SoapRequest2ScriptMapping mapping1 = new SoapRequest2ScriptMapping(new QName("namespace1","localpart1"), "script1");
		SoapRequest2ScriptMapping mapping2 = new SoapRequest2ScriptMapping(new QName("namespace2","localpart2"), "script2");
		SoapRequest2ScriptMapping mapping3 = new SoapRequest2ScriptMapping(new QName("namespace3","localpart3"), "script3");
		
		given(mockCsvReader.readAll()).willReturn(csvEntries());
		
		List<SoapRequest2ScriptMapping> actualMappings = sut.getMappings();
		
		assertThat(actualMappings, hasItems(mapping1, mapping2, mapping3));
	}
	
	@Test(description="Should throw ReadingCsvFileException when reading csv file fails",
		  expectedExceptions=ReadingCsvFileException.class)
	public void testCSVSoapRequest2ScriptMapper_csvReaderError() throws Exception{
		given(mockCsvReader.readAll()).willThrow(new IOException());
		
		sut.getMappings();
	}
	
//----------------------
// Helpers 
//----------------------
		
	private List<String[]> csvEntries() {
	  List<String[]> csvEntries = new LinkedList<String[]>();
	  csvEntries.add(new String[]{"namespace1","localpart1","script1"});
	  csvEntries.add(new String[]{"namespace2","localpart2","script2"});
	  csvEntries.add(new String[]{"namespace3","localpart3","script3"});
	  
	  return csvEntries;
  }
}
