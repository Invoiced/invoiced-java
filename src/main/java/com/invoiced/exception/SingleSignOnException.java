package com.invoiced.exception;

public final class SingleSignOnException extends InvoicedException {
  private static final long serialVersionUID = 1L;

  public SingleSignOnException(Throwable cause) {
    super(cause);
  }
}
