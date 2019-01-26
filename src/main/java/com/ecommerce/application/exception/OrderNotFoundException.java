package com.ecommerce.application.exception;

public class OrderNotFoundException extends Exception {

	public OrderNotFoundException(String exception) {
		super(exception);
	}
	public OrderNotFoundException() {
		super();
	}
}
