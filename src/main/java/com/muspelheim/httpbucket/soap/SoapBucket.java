package com.muspelheim.httpbucket.soap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.MessageEndpoint;
import org.springframework.ws.soap.SoapMessage;

import com.muspelheim.httpbucket.handler.RequestHandler;

public class SoapBucket implements MessageEndpoint {
  private static final Logger LOG = Logger.getLogger(SoapBucket.class);

  @Autowired
	public void setSoapRequestHandler(RequestHandler requestHandler){
		this.requestHandler = requestHandler;
	}
	private RequestHandler requestHandler; 

	public void invoke(MessageContext messageContext) throws Exception {
	  LOG.trace("HttpBucketEndpoint was invoked");

	  SoapMessage request = (SoapMessage)messageContext.getRequest();

		SoapMessage response = requestHandler.handleRequest(request);

		messageContext.setResponse(response);
	}
}
