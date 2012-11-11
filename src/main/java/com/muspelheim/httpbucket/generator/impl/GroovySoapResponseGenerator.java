package com.muspelheim.httpbucket.generator.impl;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ws.soap.SoapMessage;

import com.muspelheim.httpbucket.generator.SoapResponseGenerator;
import com.muspelheim.httpbucket.generator.exception.PayloadGotNotBeExtractedException;
import com.muspelheim.httpbucket.generator.exception.ResponseCouldNotBeGeneratedException;
import com.muspelheim.httpbucket.soap.util.SoapUtils;

public class GroovySoapResponseGenerator implements SoapResponseGenerator {
	private static final Logger LOG = Logger.getLogger(GroovySoapResponseGenerator.class);
	
	private static final String BINDING_NAME_RESPONSE_PAYLOAD = "responsePayload";
	private static final String BINDING_NAME_REQUEST_PAYLOAD 	= "requestPayload";
  private static final String BINDING_NAME_RESOURCE_DIR 		= "resourceDir";
	private static final String BINDING_NAME_LOG 							= "log";
	
  private FileSystemResource resourceDir;
	private String scriptName;
	
	@Autowired
	public void setSoapUtils(SoapUtils soapUtils){
		this.soapUtils = soapUtils;
	}
	private SoapUtils soapUtils;

	public GroovySoapResponseGenerator(FileSystemResource resourceDir){
		this.resourceDir 	= resourceDir;
	}

	public String generateSoapResponse(String scriptName, SoapMessage request) throws ResponseCouldNotBeGeneratedException {
		this.scriptName = scriptName;

		String requestPayload = getRequestPayload(request);
				
		return getPayloadToReturn(requestPayload);
	}
	
//--------------------
// private methods
//--------------------
  
	private String getRequestPayload(SoapMessage request){
		String payload;
		try {
			payload = soapUtils.getPayloadAsString(request);
			LOG.info("Retrieved payload from incomming request: " + payload);
		} 
		catch (Exception e) {
			LOG.error("The payload could not be extracted from the given request: " + request);
			
			throw new PayloadGotNotBeExtractedException("Payload could not be extracted from given request", e);
		}
		
		return payload;
	}
	
	private String getPayloadToReturn(String requestPayload) {
		String payloadToReturn = null;
		
		try {
			GroovyScriptEngine gse = new GroovyScriptEngine(resourceDir.getFile().getAbsolutePath());			
			Binding binding 			 = createBindings(requestPayload); 
			
			gse.run(scriptName, binding);
			payloadToReturn = (String)binding.getVariable(BINDING_NAME_RESPONSE_PAYLOAD);			
			LOG.info("Returning payload: " + payloadToReturn);
		} 
		catch (Exception e) {
		  LOG.error("The response could not be generated using script: " + scriptName, e);
	    throw new ResponseCouldNotBeGeneratedException("The response could not be generated using script: " + scriptName, e);	  
		} 
		
		return payloadToReturn;
	}	

  private Binding createBindings(String requestPayload) {
    Binding binding = new Binding();
    binding.setVariable(BINDING_NAME_REQUEST_PAYLOAD, requestPayload);
    binding.setVariable(BINDING_NAME_LOG, LOG);
    
    if(resourceDir != null)
      binding.setVariable(BINDING_NAME_RESOURCE_DIR, resourceDir.getFile().getAbsolutePath());
    
    return binding;
  }
}
