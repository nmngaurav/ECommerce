package com.ecommerce.application.exception;

public class ProductAlreadyExistsException extends Exception {

	public ProductAlreadyExistsException(String exception) {
		super(exception);
	}
	public ProductAlreadyExistsException() {
		super();
	}
}
