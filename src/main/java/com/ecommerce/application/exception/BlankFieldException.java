package com.ecommerce.application.exception;

public class BlankFieldException extends Exception {
	
	public BlankFieldException(String exception) {
		super(exception);
	}
	public BlankFieldException() {
		super();
	}

}
