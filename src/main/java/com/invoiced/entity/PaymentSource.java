package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
        @JsonSubTypes.Type(value=Card.class, name="Card"),
        @JsonSubTypes.Type(value=BankAccount.class, name="BankAccount"),
})
public class PaymentSource extends AbstractEntity {

  PaymentSource(Connection conn, Card card) {
    super(conn, Card.class);
  }

  PaymentSource(Connection conn, BankAccount account) {
    super(conn, BankAccount.class);
  }

  PaymentSource(Card card) {
    super(Card.class);
  }

  PaymentSource(BankAccount account) {
    super(BankAccount.class);
  }

  @Override
  protected boolean hasCRUD() {
    return false;
  }

  @Override
  protected boolean hasList() {
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

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public long id;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("object")
  public String object;

}