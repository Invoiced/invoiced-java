package com.invoiced.exception;

public final class EntityException extends InvoicedException {

  private static final long serialVersionUID = 1L;

  public EntityException(Throwable cause) {
    super(cause);
  }
}
