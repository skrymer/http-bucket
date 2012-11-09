package com.muspelheim.httpbucket.mapping.exception;

public class ReadingCsvFileException extends RuntimeException {
	private static final long serialVersionUID = -4672352197907428237L;

	public ReadingCsvFileException(String message, Throwable ioe) {
	  super(message, ioe);
  }
}
