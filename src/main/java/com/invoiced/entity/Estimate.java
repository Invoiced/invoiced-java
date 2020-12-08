package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public final class Estimate extends AbstractDocument<Estimate> {

  @JsonProperty(value = "invoice", access = JsonProperty.Access.WRITE_ONLY)
  public Long invoice;

  @JsonProperty(value = "approved", access = JsonProperty.Access.WRITE_ONLY)
  public Boolean approved;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("expiration_date")
  public Long expirationDate;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("deposit")
  public Long deposit;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("deposit_paid")
  public Boolean depositPaid;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("payment_terms")
  public String paymentTerms;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("ship_to")
  public ShippingDetail shipTo;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("disabled_payment_methods")
  public String[] disabledPaymentMethods;

  public Estimate(Connection conn) {
    super(conn, Estimate.class);
    this.entityName = "/estimates";
  }

  Estimate() {
    super(Estimate.class);
    this.entityName = "/estimates";
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {
      "id", "approved", "status", "subtotal", "total", "url", "pdf_url", "object", "created_at"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "approved",
      "status",
      "subtotal",
      "total",
      "url",
      "pdf_url",
      "object",
      "created_at",
      "invoice",
      "customer"
    };
  }

  public void voidEstimate() throws EntityException {
    this.voidDocument();
  }

  @JsonIgnore
  public Invoice invoice() throws EntityException {
    String url = this.getEndpoint(true) + "/invoice";

    try {
      String response = this.getConnection().post(url, null, "{}");

      Invoice invoice = Util.getMapper().readValue(response, Invoice.class);
      invoice.setConnection(this.getConnection());
      invoice.setClass(Invoice.class);

      return invoice;
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }
}
