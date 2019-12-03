package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentSource extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public long id;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("object")
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("brand")
  public String brand;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("bank_name")
  public String bankName;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("last4")
  public String last4;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("exp_month")
  public int expMonth;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("exp_year")
  public int expYear;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("routing_number")
  public String routingNumber;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("verified")
  public boolean verified;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("funding")
  public String funding;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;
}
