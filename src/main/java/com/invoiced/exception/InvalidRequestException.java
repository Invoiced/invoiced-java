package com.invoiced.exception;

public class InvalidRequestException extends InvoicedException {
  private static final long serialVersionUID = 1L;

  public InvalidRequestException(String message) {
    super(message);
  }
}
