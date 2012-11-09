package com.muspelheim.httpbucket.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.soap.SoapMessage;

import com.muspelheim.httpbucket.generator.SoapResponseGenerator;
import com.muspelheim.httpbucket.handler.RequestHandler;
import com.muspelheim.httpbucket.mapping.Request2ScriptMapper;
import com.muspelheim.httpbucket.mapping.SoapRequest2ScriptMapping;
import com.muspelheim.httpbucket.soap.util.SoapUtils;

public class SoapRequestHandlerImpl implements RequestHandler {
	
	@Autowired
	public void setSoapUtils(SoapUtils saajUtils){
		this.soapUtils = saajUtils;
	}
	private SoapUtils soapUtils;
	
	@Autowired
	public void setRequest2ScriptMapper(Request2ScriptMapper request2ScriptMapper){
		this.request2ScriptMapper = request2ScriptMapper;
	}
	private Request2ScriptMapper request2ScriptMapper;
	
	@Autowired
	public void setSoapResponseGenerator(SoapResponseGenerator soapResponseGenerator) {
  	this.soapResponseGenerator = soapResponseGenerator;
  }
	private SoapResponseGenerator soapResponseGenerator;

	public SoapMessage handleRequest(SoapMessage request) {
		List<SoapRequest2ScriptMapping> mappings = request2ScriptMapper.getMappings();
		
		for (SoapRequest2ScriptMapping mapping : mappings) {
	    
			if(canHandleRequest(mapping, request)){
	    	String response = soapResponseGenerator.generateSoapResponse(mapping.getScriptName(), request);
	    	SoapMessage soapResponse = soapUtils.createSoapMessage(response);
	    	                               
	    	return soapResponse;
	    }
    }
		
		return null;
	}

//-----------------
// private methods
//-----------------
	
	private Boolean canHandleRequest(SoapRequest2ScriptMapping mapping, SoapMessage request){
		return mapping.getQName().equals(soapUtils.getPayloadQName(request));
	}
}
