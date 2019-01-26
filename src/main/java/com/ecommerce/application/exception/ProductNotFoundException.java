package com.ecommerce.application.exception;

public class ProductNotFoundException extends Exception {

	public ProductNotFoundException(String exception) {
		super(exception);
	}
	public ProductNotFoundException() {
		super();
	}
}
