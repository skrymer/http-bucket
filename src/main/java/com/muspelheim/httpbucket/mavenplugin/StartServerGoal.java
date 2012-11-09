package com.muspelheim.httpbucket.mavenplugin;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.muspelheim.httpbucket.server.HttpBucketServer;

/**
 * Starts the http-bucket server
 * @goal start
 */
public class StartServerGoal extends AbstractMojo {
  
  /**
   * Wait for server to die 
   *
   * @parameter default-value=false
   */
  private boolean keepAlive;

  /**
   * Server port
   *
   * @parameter default-value=9080
   */
  private String serverPort;

  /**
   * Path to the request 2 script mapping file
   *
   * @parameter 
   */
  private String request2scriptMappingFile;  

  /**
   * Directory were resources used by the script can be found
   *
   * @parameter 
   */
  private String resourceDir;  
  
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    setSystemProperties();
    
    HttpBucketServer server = getHttpBucketServer(); 
    
    getLog().info("============== Starting http-bucket ==============");
    server.start();
    getLog().info("==================================================");
    
    if(keepAlive) server.join();
  }

//--------------------
// private methods
//--------------------
  
  private HttpBucketServer getHttpBucketServer(){
    HttpBucketServer server = new ClassPathXmlApplicationContext("http-bucket-server-context.xml").getBean(HttpBucketServer.class);
    
    ServerContext.set(server);

    return server; 
  }
  
  private void setSystemProperties() throws MojoFailureException {
    validateProperties();    
    
    getLog().info("============== http-bucket - Properties ==============");
    getLog().info("Mapping file:  " + new File(request2scriptMappingFile).getAbsolutePath());
    getLog().info("Resource dir:  " + new File(resourceDir).getAbsolutePath());
    getLog().info("Server port:   " + serverPort);
    getLog().info("Keep alive:    " + keepAlive);    
    getLog().info("======================================================");
    
    System.setProperty("request.2.script.mapping.file.path", request2scriptMappingFile);
    System.setProperty("resource.dir", resourceDir);
    System.setProperty("server.port", serverPort);    
  }

  private void validateProperties() throws MojoFailureException {
    validateRequest2ScrtipFile(); 
    validateResourceDir();
    validateServerPort();
  }

  /**
   * Server-port is valid if it's a number and are in the range between 1 to 65535    
   * @throws MojoFailureException
   */
  private void validateServerPort() throws MojoFailureException {
    try{
      Integer portNumber = Integer.parseInt(serverPort);
      if(portNumber < 1 || portNumber > 65535)
        throw new MojoFailureException("The given serverport is not inside the valid range: 1 - 65535");         
    }
    catch (NumberFormatException e) {
      throw new MojoFailureException("http-bucket: The given port number is not a number");       
    }
  }

  private void validateResourceDir() throws MojoFailureException {
    if(resourceDir == null || ! new File(resourceDir).exists())
      throw new MojoFailureException("http-bucket: The resource directory does not exist: " + resourceDir);
  }
  
  private void validateRequest2ScrtipFile() throws MojoFailureException {
    if(request2scriptMappingFile == null || ! new File(request2scriptMappingFile).exists())
      throw new MojoFailureException("http-bucket: The given request 2 script file does not exist: " + request2scriptMappingFile);
  }
}
