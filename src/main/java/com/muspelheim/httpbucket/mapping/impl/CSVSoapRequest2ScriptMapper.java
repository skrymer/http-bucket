package com.muspelheim.httpbucket.mapping.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.bytecode.opencsv.CSVReader;

import com.muspelheim.httpbucket.handler.impl.SoapRequestHandlerImpl;
import com.muspelheim.httpbucket.mapping.Request2ScriptMapper;
import com.muspelheim.httpbucket.mapping.SoapRequest2ScriptMapping;
import com.muspelheim.httpbucket.mapping.exception.ReadingCsvFileException;

public class CSVSoapRequest2ScriptMapper implements Request2ScriptMapper {
  private static final Logger LOG = Logger.getLogger(CSVSoapRequest2ScriptMapper.class);
  
  private List<String[]> csvEntries; 
  
	@Autowired
	public void setCSVReader(CSVReader csvReader){
		this.csvReader = csvReader;
	}
	private CSVReader csvReader;
		
	@Override
	public List<SoapRequest2ScriptMapping> getMappings() throws ReadingCsvFileException {
	  LOG.info("Creating mappings");
	  	  
	  List<SoapRequest2ScriptMapping> mappings = new ArrayList<SoapRequest2ScriptMapping>();
		
		try{
	    if(csvEntries == null || csvEntries.size() == 0 ){
	      csvEntries = csvReader.readAll();
	    }

		  for (String[] entry : csvEntries){ 
				mappings.add(createMapping(entry));
			}
		}
		catch (IOException ioe) {
			throw new ReadingCsvFileException(ioe.getMessage(), ioe);
		}
		
		return mappings;
	}

//------------------
// private methods
//------------------
	
	private SoapRequest2ScriptMapping createMapping(String[] entry){
		QName qName = new QName(entry[0], entry[1]);
		String scriptName = entry[2];
		
		LOG.info("Creating mapping with values: " + qName + "->" + scriptName);
		
		return new SoapRequest2ScriptMapping(qName, scriptName);
	}
}
