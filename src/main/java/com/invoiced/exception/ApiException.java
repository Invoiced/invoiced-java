package com.invoiced.exception;

public final class ApiException extends InvoicedException {

  private static final long serialVersionUID = 1L;

  public ApiException(String message) {
    super(message);
  }
}
