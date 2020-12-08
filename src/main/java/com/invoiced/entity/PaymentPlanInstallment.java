package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentPlanInstallment extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("date")
  public Long date;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("amount")
  public Long amount;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("balance")
  public Long balance;
}
