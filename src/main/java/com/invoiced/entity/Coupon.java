package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class Coupon extends AbstractEntity<Coupon> {

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
    @JsonProperty("exclusive")
    public Boolean exclusive;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("duration")
    public Long duration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("expiration_date")
    public Long expirationDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("max_redemptions")
    public Long maxRedemptions;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;

    @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long updatedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("metadata")
    public Object metadata;

    public Coupon(Connection conn) {
        super(conn, Coupon.class);
        this.entityName = "/coupons";
    }

    Coupon() {
        super(Coupon.class);
        this.entityName = "/coupons";
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
                "currency",
                "value",
                "is_percent",
                "exclusive",
                "expiration_date",
                "max_redemptions",
                "created_at"
        };
    }
}
