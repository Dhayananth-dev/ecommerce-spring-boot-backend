package com.api1.exception;

public class OutOfStockException extends RuntimeException{
	public OutOfStockException(String message) {
		super(message);
	}
}

