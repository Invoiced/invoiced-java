package com.invoiced.exception;

public class AuthException extends InvoicedException {

	private static final long serialVersionUID = 1L;

	public AuthException(String message) {
		super(message);
	}

}