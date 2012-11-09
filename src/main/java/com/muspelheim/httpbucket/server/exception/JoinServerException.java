package com.muspelheim.httpbucket.server.exception;


public class JoinServerException extends HttpBucketServerException {
	private static final long serialVersionUID = -8091951532259055155L;

	public JoinServerException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}
