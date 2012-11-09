package com.muspelheim.httpbucket.generator.exception;

public class ResponseCouldNotBeGeneratedException extends RuntimeException {
	private static final long serialVersionUID = -1993458467600428428L;

	public ResponseCouldNotBeGeneratedException(String message){
		super(message);
	}
	
	public ResponseCouldNotBeGeneratedException(String message, Throwable cause){
		super(message, cause);
	}
}
