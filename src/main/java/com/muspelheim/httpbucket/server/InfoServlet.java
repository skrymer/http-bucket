package com.muspelheim.httpbucket.server;

import java.io.IOException;
import java.io.PrintWriter;

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
		PrintWriter out = resp.getWriter();
		
		out.println("<h1>Welcome to http-bucket</h1>");
		out.println("<hr/>");
		printInfo(out);
		printSoapMappings(out);		

		out.flush();
	}

//-----------------
// private methods
//-----------------
	
	private void printInfo(PrintWriter out) {
    out.println("<div>");
    out.println("<h3>Info:</h3>"); 
    out.println("<ul>");    
    out.println("<li>Mapping file: " + System.getProperty("request.2.script.mapping.file.path") + "</li>");
    out.println("<li>Resource dir: " + System.getProperty("resource.dir") + "</li>");
    out.println("</ul>");        
    out.println("</div>");    
  }

  private void printSoapMappings(PrintWriter out) throws IOException {
    out.println("<div>");
    out.println("<h3>Soap request 2 script mappings:</h3>");
    out.println("<ul>");	  
    for (SoapRequest2ScriptMapping mapping : soapRequestMapper.getMappings()) {
	    out.println("<li>");
	    out.println(mapping.toString());
	    out.println("</li>");		
    }
	  out.println("</ul>");
	  out.println("<div>");
  }
}
