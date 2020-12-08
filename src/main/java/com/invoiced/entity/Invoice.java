package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public final class Invoice extends AbstractDocument<Invoice> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("due_date")
  public long dueDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("payment_terms")
  public String paymentTerms;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("autopay")
  public Boolean autopay;

  @JsonProperty(value = "paid", access = JsonProperty.Access.WRITE_ONLY)
  public Boolean paid;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("sent")
  public Boolean sent;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("chase")
  public Boolean chase;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("next_chase_on")
  public long nextChaseOn;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("attempt_count")
  public long attemptCount;

  @JsonProperty(value = "next_payment_attempt", access = JsonProperty.Access.WRITE_ONLY)
  public long nextPaymentAttempt;

  @JsonProperty(value = "subscription", access = JsonProperty.Access.WRITE_ONLY)
  public long subscription;

  @JsonProperty(value = "balance", access = JsonProperty.Access.WRITE_ONLY)
  public double balance;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("tags")
  public Object[] tags;

  @JsonProperty(value = "payment_url", access = JsonProperty.Access.WRITE_ONLY)
  public String paymentUrl;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("ship_to")
  public Object shipTo;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("disabled_payment_methods")
  public String[] disabledPaymentMethods;

  public Invoice(Connection conn) {
    super(conn, Invoice.class);
    this.entityName = "/invoices";
  }

  Invoice() {
    super(Invoice.class);
    this.entityName = "/invoices";
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
      "paid",
      "status",
      "total",
      "url",
      "pdf_url",
      "object",
      "created_at",
      "attempt_count",
      "next_payment_attempt",
      "subscription",
      "subtotal",
      "balance",
      "payment_url"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "paid",
      "status",
      "total",
      "url",
      "pdf_url",
      "object",
      "created_at",
      "attempt_count",
      "next_payment_attempt",
      "subscription",
      "subtotal",
      "balance",
      "payment_url"
    };
  }

  @JsonIgnore
  public TextMessage[] sendText(TextRequest textRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/text_messages";

    try {
      String textRequestJson = textRequest.toJsonString();

      String response = this.getConnection().post(url, null, textRequestJson);

      return Util.getMapper().readValue(response, TextMessage[].class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Letter sendLetter() throws EntityException {
    String url = this.getEndpoint(true) + "/letters";

    try {
      String response = this.getConnection().post(url, null, "{}");

      return Util.getMapper().readValue(response, Letter.class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public void pay() throws EntityException {
    String url = this.getEndpoint(true) + "/pay";

    try {
      String response = this.getConnection().post(url, null, "");

      Invoice invoice = Util.getMapper().readValue(response, Invoice.class);

      setFields(invoice, this);
    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public void voidInvoice() throws EntityException {
    this.voidDocument();
  }

  @JsonIgnore
  public Note newNote() {
    Note note = new Note(this.getConnection());
    note.setEndpointBase(this.getEndpoint(true));
    return note;
  }

  @JsonIgnore
  public PaymentPlan newPaymentPlan() {
    PaymentPlan paymentPlan = new PaymentPlan(this.getConnection());
    paymentPlan.setEndpointBase(this.getEndpoint(true));
    return paymentPlan;
  }
}
