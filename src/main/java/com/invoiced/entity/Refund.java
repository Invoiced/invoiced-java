package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public final class Refund extends AbstractEntity<Refund> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public long id;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("charge")
    public long charge;

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
    @JsonProperty("currency")
    public String currency;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("amount")
    public double amount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("failure_reason")
    public String failureReason;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;

    public Refund(Connection conn) {
        super(conn, Refund.class);
        this.entityName = "/refunds";
    }

    Refund() {
        super(Refund.class);
        this.entityName = "/refunds";
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
                "id", "failure_reason", "created_at"
        };
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{
                "id",
                "created_at"
        };
    }

    @JsonIgnore
    public void create(long chargeId, double amount) throws EntityException {
        String url = "/charges/" + chargeId + "/refunds";

        RefundRequest refundRequest = new RefundRequest(amount);

        try {
            String refundRequestJson = refundRequest.toJsonString();

            String response = this.getConnection().post(url, null, refundRequestJson);

            Refund refund = Util.getMapper().readValue(response, Refund.class);
            setFields(refund, this);
        } catch (Throwable c) {
            throw new EntityException(c);
        }
    }
}
