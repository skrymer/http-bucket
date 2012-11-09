package com.muspelheim.httpbucket.mavenplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Stops the http-bucket server
 * @goal stop
 */
public class StopServerGoal extends AbstractMojo {

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("============== Stopping http-bucket ==============");
    ServerContext.get().stop();
    ServerContext.unset();
    getLog().info("==================================================");    
  }
}
