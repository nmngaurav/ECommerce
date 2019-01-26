package com.ecommerce.application.exception;

public class InvalidQuantityException extends Exception {

	public InvalidQuantityException(String exception) {
		super(exception);
	}
	public InvalidQuantityException() {
		super();
	}
}
