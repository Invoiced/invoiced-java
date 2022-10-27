package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class Item extends AbstractEntity<Item> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public String id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("name")
  public String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("unit_cost")
  public Double unitCost;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("description")
  public String description;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  public String type;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxable")
  public Boolean taxable;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxes")
  public TaxRate[] taxes;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("avalara_tax_code")
  public String avalaraTaxCode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("avalara_location_code")
  public String avalaraLocationCode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("gl_account")
  public String glAccount;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("discountable")
  public Boolean discountable;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;

  public Item(Connection conn) {
    super(conn, Item.class);
    this.entityName = "/items";
  }

  Item() {
    super(Item.class);
    this.entityName = "/items";
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return this.id;
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {"object", "created_at"};
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "currency",
      "object",
      "unit_cost",
      "taxable",
      "taxes",
      "avalara_tax_code",
      "discountable",
      "gl_account",
      "created_at"
    };
  }
}
