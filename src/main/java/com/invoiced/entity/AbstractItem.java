package com.invoiced.entity;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public abstract class AbstractItem {

  @Override
  public String toString() {
    try {
      return super.toString() + " JSON: " + this.toJsonString();
    } catch (Throwable c) {
      throw new RuntimeException(c);
    }
  }

  public String toJsonString() throws EntityException {
    try {
      return Util.getMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(this);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }
}
