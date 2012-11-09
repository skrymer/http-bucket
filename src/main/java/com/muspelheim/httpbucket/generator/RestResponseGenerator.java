package com.muspelheim.httpbucket.generator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestResponseGenerator {
	
	/**
	 * Sets the content in the response to the value returned from the given script
	 * 
	 * @param request
	 * @param response
	 */
	void setHttpResponseContent(HttpServletRequest request, HttpServletResponse response);
}
