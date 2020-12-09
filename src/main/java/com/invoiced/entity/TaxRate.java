package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class TaxRate extends AbstractEntity<TaxRate> {

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
  @JsonProperty("value")
  public Double value;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("is_percent")
  public Boolean isPercent;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("inclusive")
  public Boolean inclusive;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;

  public TaxRate(Connection conn) {
    super(conn, TaxRate.class);
    this.entityName = "/tax_rates";
  }

  TaxRate() {
    super(TaxRate.class);
    this.entityName = "/tax_rates";
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
      "id", "object", "currency", "value", "inclusive", "is_percent", "created_at"
    };
  }
}
