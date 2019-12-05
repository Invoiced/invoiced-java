package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public class Transaction extends AbstractEntity<Transaction> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public long id;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("customer")
  public long customer;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("invoice")
  public long invoice;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("credit_note")
  public long creditNote;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("date")
  public long date;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  public String type;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("method")
  public String method;
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
  public Object paymentSource;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public double amount;
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("fee")
  public double fee;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("notes")
  public String notes;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("failure_reason")
  public String failureReason;
  @JsonProperty(value = "parent_transaction", access = JsonProperty.Access.WRITE_ONLY)
  public long parentTransaction;
  @JsonProperty(value = "pdf_url", access = JsonProperty.Access.WRITE_ONLY)
  public String pdfUrl;
  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public long createdAt;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;

  public Transaction(Connection conn) {
    super(conn, Transaction.class);
    this.entityName = "/transactions";
  }

  Transaction() {
    super(Transaction.class);
    this.entityName = "/transactions";
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
      "id", "payment_source", "failure_reason", "parent_transaction", "pdf_url", "created_at"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "customer",
      "invoice",
      "credit_note",
      "type",
      "customer_id",
      "complete",
      "completed_date",
      "completed_by_user_id",
      "chase_step_id",
      "created_at"
    };
  }

  @JsonIgnore
  public Transaction refund(long amount) throws EntityException {

    String url = this.getEndpoint(true) + "/refunds";

    RefundRequest refundRequest = new RefundRequest(amount);

    Transaction v1 = null;

    try {

      String refundToJson = refundRequest.toJsonString();

      String response = this.getConnection().post(url, null, refundToJson);

      v1 = Util.getMapper().readValue(response, Transaction.class);
      v1.setConnection(this.getConnection());
      v1.setClass(Transaction.class);

    } catch (Throwable c) {

      throw new EntityException(c);
    }

    return v1;
  }

  @JsonIgnore
  public Email[] send(EmailRequest emailRequest) throws EntityException {

    String url = this.getEndpoint(true) + "/emails";

    Email[] emails = null;

    try {

      String emailRequestJson = emailRequest.toJsonString();

      String response = this.getConnection().post(url, null, emailRequestJson);

      emails = Util.getMapper().readValue(response, Email[].class);

    } catch (Throwable c) {

      throw new EntityException(c);
    }

    return emails;
  }

  @JsonIgnore
  public Transaction initiateCharge(ChargeRequest chargeRequest) throws EntityException {

    String url = "/charges";

    Transaction transaction = null;

    try {

      String chargeRequestJson = chargeRequest.toJsonString();

      String response = this.getConnection().post(url, null, chargeRequestJson);

      transaction = Util.getMapper().readValue(response, Transaction.class);

    } catch (Throwable c) {

      throw new EntityException(c);
    }

    return transaction;
  }
}
