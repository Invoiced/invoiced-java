package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Letter extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("id")
  public String id;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("state")
  public String state;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("num_pages")
  public long numPages;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("expected_delivery_date")
  public long expectedDeliveryDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("to")
  public String to;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("created_at")
  public long createdAt;
}
