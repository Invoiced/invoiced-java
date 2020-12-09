package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public final class CreditBalanceAdjustment extends AbstractEntity<CreditBalanceAdjustment> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public Long id;

    @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
    public String object;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("customer")
    public Long customer;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("date")
    public Long date;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("currency")
    public String currency;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("amount")
    public Double amount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("notes")
    public String notes;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;

    public CreditBalanceAdjustment(Connection conn) {
        super(conn, CreditBalanceAdjustment.class);
        this.entityName = "/credit_balance_adjustments";
    }

    CreditBalanceAdjustment() {
        super(CreditBalanceAdjustment.class);
        this.entityName = "/credit_balance_adjustments";
    }

    @Override
    @JsonIgnore
    protected String getEntityId() {
        return String.valueOf(this.id);
    }

    @Override
    @JsonIgnore
    protected String[] getCreateExclusions() {
        return new String[] {
            "id",
            "created_at"
        };
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[] {
            "id",
            "created_at"
        };
    }
}
