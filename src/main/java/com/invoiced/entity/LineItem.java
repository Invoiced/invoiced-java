package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class LineItem extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("catalog_item")
  public String item;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  public String type;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("name")
  public String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("description")
  public String description;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("quantity")
  public Double quantity;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("unit_cost")
  public Double unitCost;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public Double amount;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("discountable")
  public Boolean discountable;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("discounts")
  public Discount[] discounts;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxable")
  public Boolean taxable;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxes")
  public Tax[] taxes;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("plan")
  public String plan;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("period_start")
  public Long periodStart;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("period_end")
  public Long periodEnd;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("prorated")
  public Boolean prorated;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("subscription")
  public Long subscription;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;
}
