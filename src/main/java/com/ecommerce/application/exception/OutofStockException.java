package com.ecommerce.application.exception;

public class OutofStockException extends Exception {

	public OutofStockException(String exception) {
		super(exception);
	}
	public OutofStockException() {
		super();
	}
}
