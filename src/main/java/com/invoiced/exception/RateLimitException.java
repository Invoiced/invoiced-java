package com.invoiced.exception;

public class RateLimitException extends InvoicedException {
	private static final long serialVersionUID = 1L;

	public RateLimitException(String message) {
		super(message);
	}
}
