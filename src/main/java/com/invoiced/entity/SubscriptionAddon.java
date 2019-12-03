package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionAddon extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("description")
  public String description;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("plan")
  public String plan;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("catalog_item")
  public String catalogItem;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("quantity")
  public long quantity;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public long createdAt;
}
