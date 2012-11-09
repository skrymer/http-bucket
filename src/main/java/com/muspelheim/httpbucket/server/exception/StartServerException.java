package com.muspelheim.httpbucket.server.exception;


public class StartServerException extends HttpBucketServerException {
  private static final long serialVersionUID = 8379633744903450914L;

	public StartServerException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}
