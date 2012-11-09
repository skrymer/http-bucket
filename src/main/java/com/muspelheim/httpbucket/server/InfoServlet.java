package com.muspelheim.httpbucket.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.muspelheim.httpbucket.mapping.Request2ScriptMapper;
import com.muspelheim.httpbucket.mapping.SoapRequest2ScriptMapping;

public class InfoServlet extends HttpServlet {
  private static final long serialVersionUID = -4289367687238694797L;

  @Autowired
  @Qualifier("soapRequest2ScriptMapper")
  private Request2ScriptMapper soapRequestMapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getOutputStream().println("<h1>Welcome to http-bucket</h1>");
		resp.getOutputStream().println("<h2>Info:</h2>");
		resp.getOutputStream().println("Resource dir: " + System.getProperty("resource.dir"));
		resp.getOutputStream().println("<div>");
		resp.getOutputStream().println("<h3>Soap request 2 script mappings:</h3>");
		resp.getOutputStream().println("<ul>");
		printSoapMappings(resp);		
		resp.getOutputStream().println("</ul>");
		resp.getOutputStream().println("<div>");
	}

//-----------------
// private methods
//-----------------
	
	private void printSoapMappings(HttpServletResponse resp) throws IOException {
	  for (SoapRequest2ScriptMapping mapping : soapRequestMapper.getMappings()) {
			resp.getOutputStream().println("<li>");
			resp.getOutputStream().println(mapping.toString());
			resp.getOutputStream().println("</li>");		
    }
  }
}
