package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class BankAccount extends PaymentSource {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("bank_name")
  public String bankName;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("last4")
  public String last4;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("routing_number")
  public String routingNumber;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("verified")
  public boolean verified;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;

  BankAccount() {
    super();
    setClass(BankAccount.class);
    this.entityName = "/bank_accounts";
  }

  public void delete() throws EntityException {
    try {

      this.getConnection().delete(this.getEndpoint(true));

    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }
}
