package com.muspelheim.httpbucket.server.exception;


public class StopServerException extends HttpBucketServerException {
  private static final long serialVersionUID = -276539184738781435L;

	public StopServerException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}
