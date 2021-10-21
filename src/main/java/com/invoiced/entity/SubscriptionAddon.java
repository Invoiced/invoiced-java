package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SubscriptionAddon extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("description")
  public String description;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("plan")
  public String plan;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("quantity")
  public Long quantity;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public Double amount;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;
}
