package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public final class Charge extends AbstractEntity<Charge> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public Long id;

    @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
    public String object;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("customer")
    public Long customer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("status")
    public String status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("gateway")
    public String gateway;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("gateway_id")
    public String gatewayId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("payment_source")
    public PaymentSource paymentSource;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("currency")
    public String currency;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("amount")
    public Double amount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("failure_message")
    public String failureMessage;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("amount_refunded")
    public Double amountRefunded;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("refunded")
    public Boolean refunded;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("refunds")
    public Refund[] refunds;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("disputed")
    public Boolean disputed;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;

    public Charge(Connection conn) {
        super(conn, Charge.class);
        this.entityName = "/charges";
    }

    Charge() {
        super(Charge.class);
        this.entityName = "/charges";
    }

    @Override
    @JsonIgnore
    protected String getEntityId() {
        return String.valueOf(this.id);
    }

    @Override
    @JsonIgnore
    protected String[] getCreateExclusions() {
        return new String[]{
                "id", "payment_source", "failure_reason", "amount_refunded", "refunded", "created_at"
        };
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{
                "id",
                "customer",
                "created_at",
                "amount_refunded",
                "refunded"
        };
    }

    @JsonIgnore
    public Payment create(ChargeRequest chargeRequest) throws EntityException {
        try {
            String chargeRequestJson = chargeRequest.toJsonString();

            String response = this.getConnection().post("/charges", null, chargeRequestJson);

            return Util.getMapper().readValue(response, Payment.class);
        } catch (Throwable c) {

            throw new EntityException(c);
        }
    }
}
