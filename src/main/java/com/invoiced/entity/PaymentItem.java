package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentItem extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("type")
  public String type;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("invoice")
  public long invoice;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("estimate")
  public long estimate;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public long amount;
}
