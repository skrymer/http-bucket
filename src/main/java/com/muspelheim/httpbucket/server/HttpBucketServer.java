package com.muspelheim.httpbucket.server;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import com.muspelheim.httpbucket.rest.RestfulBucket;
import com.muspelheim.httpbucket.server.exception.JoinServerException;
import com.muspelheim.httpbucket.server.exception.StartServerException;
import com.muspelheim.httpbucket.server.exception.StopServerException;

public class HttpBucketServer {
  private static final Logger LOG = Logger.getLogger(HttpBucketServer.class);

  private Server server;
  
  @Value("${server.port}") 
  private Integer serverPort;
  @Autowired 
  private InfoServlet infoServlet;
  @Autowired 
  private MessageDispatcherServlet soapServlet;
  @Autowired
  private RestfulBucket restServlet;
  
  /**
   * Starts the embedded Jetty server on the given port
   * 
   * @throws StartServerException if the embedded Jetty server throws an exception during startup  
   */
  public void start() throws StartServerException{
  	ServletHolder soapServletHolder = new ServletHolder("soap", soapServlet);
    soapServletHolder.setInitParameter("contextConfigLocation", "classpath:soap-context.xml");

    ServletHolder restServletHolder = new ServletHolder("rest", restServlet);
    
    ServletHolder inforServletHolder = new ServletHolder("info", infoServlet);
    
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(soapServletHolder, "/soap");
    context.addServlet(restServletHolder, "/rest");
    context.addServlet(inforServletHolder,"/");
    
    server = new Server(serverPort);
    server.setHandler(context);
    
    try{
      server.start();
    }
    catch (Exception e) {
    	LOG.error("Error while trying to start http-bucket: " + e.getMessage(), e);
    	throw new StartServerException("Error while trying to start http-bucket: " + e.getMessage(), e);
    }
  }
  
  /**
   * Starts the embedded Jetty server
   * @throws JoinServerException if the embedded Jetty server throws an exception during joining 
   */
  public void join() throws JoinServerException{
    try{
      server.join();
    }
    catch (Exception e) {
    	LOG.error("Error while trying to join server thread: " + e.getMessage(), e);
    	throw new JoinServerException("Error while trying to join server thread: " + e.getMessage(), e);
    }
  }
  
  /**
   * Stops the embedded Jetty server
   * @throws StopServerException if the embedded Jetty server throws an exception during stopping
   */
  public void stop() throws StopServerException{
    try{
      server.stop();
    }
    catch (Exception e) {
    	LOG.error("Error while trying to stop http-bucket: " + e.getMessage(), e);
      throw new StopServerException("Error while trying to stop the http-bucket server: " + e.getMessage(), e);    
    }  
  }  
}
