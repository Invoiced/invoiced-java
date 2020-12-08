package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SubscriptionPreview extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("first_invoice")
  public Invoice firstInvoice;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("mrr")
  public Long mrr;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("recurring_total")
  public Long recurring_total;
}
