package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PaymentItem extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("type")
  public String type;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public Double amount;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("invoice")
  public Long invoice;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("estimate")
  public Long estimate;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("credit_note")
  public Long creditNote;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("document_type")
  public Long documentType;
}
