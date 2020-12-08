package com.invoiced.entity;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "object")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Card.class, name = "card"),
  @JsonSubTypes.Type(value = BankAccount.class, name = "bank_account")
})
public class PaymentSource extends AbstractEntity {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("gateway")
  public String gateway;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("gateway_id")
  public String gatewayId;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("gateway_customer")
  public String gatewayCustomer;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("chargeable")
  public Boolean chargeable;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("'failure_reason'")
  public String failureReason;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("receipt_email")
  public String receiptEmail;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  PaymentSource(Connection conn) {
    super(conn, PaymentSource.class);
    this.entityName = "/payment_sources";
  }

  PaymentSource() {
    super(PaymentSource.class);
    this.entityName = "/payment_sources";
  }

  @Override
  protected boolean hasCRUD() {
    return false;
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return String.valueOf(this.id);
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {};
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {};
  }
}
