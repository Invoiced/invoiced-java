package com.invoiced.entity;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "object")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Card.class, name = "card"),
  @JsonSubTypes.Type(value = BankAccount.class, name = "bank_account")
})
public class PaymentSource extends AbstractEntity {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public long id;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("object")
  public String object;

  PaymentSource(Connection conn) {
    super(conn, PaymentSource.class);
    this.entityName = "/payment_sources";
  }

  PaymentSource() {
    super(PaymentSource.class);
    this.entityName = "/payment_sources";
  }

  @Override
  protected boolean hasCRUD() {
    return false;
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return String.valueOf(this.id);
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {};
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {};
  }
}
