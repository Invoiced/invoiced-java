package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BalanceHistory extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("timestamp")
  public Long timestamp;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("balance")
  public Double balance;
}
