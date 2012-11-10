package com.muspelheim.httpbucket.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.muspelheim.httpbucket.handler.RequestHandler;

public class RestfulBucket extends HttpServlet {
  private static final long serialVersionUID = 7137368752006403671L;
  
  @Autowired 
  private RequestHandler<String, HttpServletRequest> requestHandler;
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	response.getWriter().print(requestHandler.handleRequest(request));
  	response.getWriter().flush();
  }
}
