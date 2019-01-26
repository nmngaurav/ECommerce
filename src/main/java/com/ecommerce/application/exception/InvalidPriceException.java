package com.ecommerce.application.exception;

public class InvalidPriceException extends Exception {

	public InvalidPriceException(String exception) {
		super(exception);
	}
	public InvalidPriceException() {
		super();
	}
}
