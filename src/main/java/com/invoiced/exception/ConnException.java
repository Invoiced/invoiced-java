package com.invoiced.exception;

public final class ConnException extends InvoicedException {

  private static final long serialVersionUID = 1L;

  public ConnException(Throwable cause) {
    super(cause);
  }
}
