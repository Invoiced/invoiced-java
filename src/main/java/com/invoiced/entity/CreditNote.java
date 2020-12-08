package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public class CreditNote extends AbstractDocument<CreditNote> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("invoice")
  public long invoice;

  @JsonProperty(value = "paid", access = JsonProperty.Access.WRITE_ONLY)
  public Boolean paid;

  @JsonProperty(value = "balance", access = JsonProperty.Access.WRITE_ONLY)
  public double balance;

  public CreditNote(Connection conn) {
    super(conn, CreditNote.class);
    this.entityName = "/credit_notes";
  }

  CreditNote() {
    super(CreditNote.class);
    this.entityName = "/credit_notes";
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return String.valueOf(this.id);
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {
      "id", "paid", "status", "total", "balance", "url", "pdf_url", "object", "created_at"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "paid",
      "status",
      "total",
      "balance",
      "url",
      "pdf_url",
      "object",
      "created_at",
      "invoice",
      "customer"
    };
  }

  public void voidCreditNote() throws EntityException {
    this.voidDocument();
  }
}
