package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class Plan extends AbstractEntity<Plan> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public String id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("object")
    public String object;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("catalog_item")
    public String item;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("name")
    public String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("currency")
    public String currency;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("amount")
    public Long amount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("pricing_mode")
    public String pricingMode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("quantity_type")
    public String quantityType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("interval")
    public String interval;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("interval_count")
    public Long intervalCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("tiers")
    public Object[] tiers;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("metadata")
    public Object metadata;

    public Plan(Connection conn) {
        super(conn, Plan.class);
        this.entityName = "/plans";
    }

    Plan() {
        super(Plan.class);
        this.entityName = "/plans";
    }

    @Override
    @JsonIgnore
    protected String getEntityId() {
        return this.id;
    }

    @Override
    @JsonIgnore
    protected String[] getCreateExclusions() {
        return new String[]{"object", "created_at"};
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{
                "id",
                "object",
                "catalog_item",
                "currency",
                "amount",
                "pricing_mode",
                "quantity_type",
                "interval",
                "interval_count",
                "tiers",
                "created_at"
        };
    }
}
