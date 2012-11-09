package com.muspelheim.httpbucket.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ws.soap.SoapMessage;

public interface RequestHandler {

  /**
   *
   * @param request
   * @return
   * 		The SoapMessage returned by the related script 
   */
  SoapMessage handleRequest(SoapMessage request) ;

  /**
   * 
   * @param restRequest
   * @return
   *     The response returned by the related script 
   */
	String handleRequest(HttpServletRequest restRequest);
}
