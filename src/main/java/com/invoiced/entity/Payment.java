package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public final class Payment extends AbstractEntity<Payment> {

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
  @JsonProperty("method")
  public String method;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("matched")
  public Boolean matched;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("voided")
  public Boolean voided;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("status")
  public String status;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public Double amount;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("balance")
  public Double balance;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("reference")
  public String reference;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("source")
  public String source;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("notes")
  public String notes;

  @JsonProperty(value = "pdf_url", access = JsonProperty.Access.WRITE_ONLY)
  public String pdfUrl;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("charge")
  public Charge charge;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("applied_to")
  public PaymentItem[] appliedTo;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;

  public Payment(Connection conn) {
    super(conn, Payment.class);
    this.entityName = "/payments";
  }

  Payment() {
    super(Payment.class);
    this.entityName = "/payments";
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
            "status",
            "pdf_url",
            "balance",
            "matched",
            "voided",
            "created_at"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
            "id",
            "status",
            "pdf_url",
            "balance",
            "matched",
            "voided",
            "created_at"
    };
  }

  @JsonIgnore
  public Email[] send(EmailRequest emailRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/emails";

    try {
      String emailRequestJson = emailRequest.toJsonString();

      String response = this.getConnection().post(url, null, emailRequestJson);

      return Util.getMapper().readValue(response, Email[].class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  public void voidPayment() throws EntityException {
    this.deleteWithResponse();
  }
}
