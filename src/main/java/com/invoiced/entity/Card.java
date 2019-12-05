package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class Card extends PaymentSource {

  Card() {
    super(new Card());
  }

  Card(Connection conn) {
    super(conn, new Card());
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("brand")
  public String brand;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("last4")
  public String last4;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("exp_month")
  public int expMonth;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("exp_year")
  public int expYear;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("funding")
  public String funding;

  public void delete() throws EntityException {
    try {

      this.getConnection().delete(this.getEndpoint(true));

    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }

}