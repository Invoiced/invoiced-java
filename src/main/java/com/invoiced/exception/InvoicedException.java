package com.invoiced.exception;

public abstract class InvoicedException extends Exception {
  private static final long serialVersionUID = 1L;

  public InvoicedException(Throwable cause) {
    super(cause);
  }

  public InvoicedException(String message) {
    super(message);
  }
}
