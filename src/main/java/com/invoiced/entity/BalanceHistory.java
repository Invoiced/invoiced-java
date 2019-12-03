package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BalanceHistory extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("timestamp")
  public long timestamp;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("balance")
  public double balance;
}
