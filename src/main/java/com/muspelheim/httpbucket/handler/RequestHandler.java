package com.muspelheim.httpbucket.handler;


public interface RequestHandler<T, V> {

  /**
   *
   * @param request
   * @return
   * 		The SoapMessage returned by the related script 
   */
  T handleRequest(V request) ;
}
