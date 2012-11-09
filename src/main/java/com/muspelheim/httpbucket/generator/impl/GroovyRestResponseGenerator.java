package com.muspelheim.httpbucket.generator.impl;

import java.io.Writer;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;

import com.muspelheim.httpbucket.generator.RestResponseGenerator;
import com.muspelheim.httpbucket.generator.exception.ResponseCouldNotBeGeneratedException;

public class GroovyRestResponseGenerator implements RestResponseGenerator {
	private static final Logger LOG = Logger.getLogger(GroovySoapResponseGenerator.class);

	private static final String BINDING_NAME_HTTP_REQUEST 	  = "httpRequest";
	private static final String BINDING_NAME_LOG 		  				= "log";
	private static final String BINDING_NAME_RESOURCE_DIR 	  = "resourceDir";
	private static final String BINDING_NAME_RESPONSE_CONTENT = "responseContent";
	
  private FileSystemResource resourceDir;
	private FileSystemResource scriptFile;
	
	public GroovyRestResponseGenerator(){
		
	}
	
	public GroovyRestResponseGenerator(FileSystemResource scriptFile, FileSystemResource resourceDir){
		this.scriptFile 	= scriptFile;
		this.resourceDir 	= resourceDir;
	}
	
	@Override
	public void setHttpResponseContent(HttpServletRequest request, HttpServletResponse response) throws ResponseCouldNotBeGeneratedException{
		String content = getResponseContent(request);
		setContentOnReponse(content, response);
	}

//-------------------
// private methods
//-------------------

	private String getResponseContent(HttpServletRequest request) {
		String responseContent = null;
		
		try {
			GroovyScriptEngine gse = new GroovyScriptEngine(scriptFile.getFile().getAbsolutePath());			
			Binding binding = createBindings(request); 

			//Run script
			gse.run(scriptFile.getFilename(), binding);
			
			//Get outcome
			responseContent = (String)binding.getVariable(BINDING_NAME_RESPONSE_CONTENT);
		}
		catch(Exception e){
			throwGeneratorException(e);
		}
		
		return responseContent;
  }
	
	private void setContentOnReponse(String content, HttpServletResponse response) {
		try{
			Writer writer = response.getWriter();
			writer.write(content);
			writer.flush();
		}
		catch(Exception e){
			throwGeneratorException(e);
		}
  }

  private Binding createBindings(HttpServletRequest request) {
    Binding binding = new Binding();
    binding.setVariable(BINDING_NAME_HTTP_REQUEST, request);
    binding.setVariable(BINDING_NAME_LOG, LOG);
    
    if(resourceDir != null)
      binding.setVariable(BINDING_NAME_RESOURCE_DIR, resourceDir.getFile().getAbsolutePath());
    
    return binding;
  }

  private void throwGeneratorException(Exception e){
    //TODO Externalize exception message
	  LOG.error("Couldn't execute groovy script: " + e.getMessage(), e);
    throw new ResponseCouldNotBeGeneratedException("Something went wrong while executing scripts: " + e.getMessage(), e);	  
	}
}
