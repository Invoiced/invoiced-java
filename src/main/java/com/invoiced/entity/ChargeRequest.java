package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ChargeRequest extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("customer")
  public long customer;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("method")
  public long method;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public long amount;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("invoiced_token")
  public String invoicedToken;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("gateway_token")
  public String gatewayToken;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("payment_source_type")
  public String paymentSourceType;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("payment_source_id")
  public Long paymentSourceId;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("vault_method")
  public Boolean vaultMethod;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("make_default")
  public Boolean makeDefault;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("receipt_email")
  public String receiptEmail;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("applied_to")
  public PaymentItem[] appliedTo;
}
