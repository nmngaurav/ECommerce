package com.ecommerce.application.exception;

public class InvalidEmailException extends Exception {
	public InvalidEmailException(String exception) {
		super(exception);
	}

	public InvalidEmailException() {
		super();
	}
}
