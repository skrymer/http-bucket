package com.muspelheim.httpbucket.generator.exception;

public class PayloadGotNotBeExtractedException extends RuntimeException {
	private static final long serialVersionUID = -1993458467600428428L;
	
	public PayloadGotNotBeExtractedException(String message){
		super(message);
	}
	
	public PayloadGotNotBeExtractedException(String message, Throwable cause){
		super(message, cause);
	}

}
