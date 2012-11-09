package com.muspelheim.httpbucket.server.exception;

/**
 * @author Sonni Nielsen - nielses
 *
 */
public class HttpBucketServerException extends RuntimeException {
  private static final long serialVersionUID = 4308101761101911762L;

  public HttpBucketServerException(String msg, Throwable t){
    super(msg, t);
  }
}
