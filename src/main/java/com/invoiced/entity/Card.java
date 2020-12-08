package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public final class Card extends PaymentSource {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("brand")
    public String brand;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("last4")
    public String last4;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("exp_month")
    public Long expMonth;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("exp_year")
    public Long expYear;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("funding")
    public String funding;

    Card() {
        super();
        setClass(Card.class);
        this.entityName = "/cards";
    }

    public void delete() throws EntityException {
        try {
            this.getConnection().delete(this.getEndpoint(true));
        } catch (Throwable c) {
            throw new EntityException(c);
        }
    }
}
