package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Discount extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public Double amount;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("coupon")
  public Coupon coupon;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("expires")
  public Long expires;
}
