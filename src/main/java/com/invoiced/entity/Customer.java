package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public final class Customer extends AbstractEntity<Customer> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("name")
  public String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("number")
  public String number;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("email")
  public String email;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("autopay")
  public Boolean autopay;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("autopay_delay_days")
  public Long autopayDelayDays;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("payment_terms")
  public String paymentTerms;

  @JsonProperty(value = "payment_source", access = JsonProperty.Access.WRITE_ONLY)
  public PaymentSource paymentSource;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("disabled_payment_methods")
  public String[] disabledPaymentMethods;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  public String type;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("attention_to")
  public String attentionTo;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("address1")
  public String address1;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("address2")
  public String address2;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("city")
  public String city;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("state")
  public String state;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("postal_code")
  public String postalCode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("country")
  public String country;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("language")
  public String language;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("chase")
  public Boolean chase;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("chasing_cadence")
  public Long chasingCadence;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("next_chase_step")
  public Long nextChaseStep;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxable")
  public Boolean taxable;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("tax_id")
  public String taxId;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxes")
  public TaxRate[] taxes;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("avalara_entity_use_code")
  public String avalaraEntityUseCode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("avalara_exemption_number")
  public String avalaraExemptionNumber;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("phone")
  public String phone;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("credit_hold")
  public Boolean creditHold;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("credit_limit")
  public Long creditLimit;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("owner")
  public Long owner;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("notes")
  public String notes;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("parent_customer")
  public Long parentCustomer;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("bill_to_parent")
  public Boolean billToParent;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("sign_up_page")
  public Long signUpPage;

  @JsonProperty(value = "sign_up_url", access = JsonProperty.Access.WRITE_ONLY)
  public String signUpUrl;

  @JsonProperty(value = "statement_pdf_url", access = JsonProperty.Access.WRITE_ONLY)
  public String statementPdfUrl;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;

  public Customer(Connection conn) {
    super(conn, Customer.class);
    this.entityName = "/customers";
  }

  Customer() {
    super(Customer.class);
    this.entityName = "/customers";
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
      "object",
      "payment_source",
      "next_chase_step",
      "sign_up_url",
      "statement_pdf_url",
      "created_at"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "object",
      "payment_source",
      "next_chase_step",
      "sign_up_url",
      "statement_pdf_url",
      "created_at"
    };
  }

  @JsonIgnore
  public Balance getBalance() throws EntityException {
    String url = this.getEndpoint(true) + "/balance";

    try {
      String response = this.getConnection().get(url, null);

      return Util.getMapper().readValue(response, Balance.class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Email[] sendStatement(EmailRequest emailRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/emails";

    try {
      String emailRequestJson = emailRequest.toJsonString();

      String response = this.getConnection().post(url, null, emailRequestJson);

      return Util.getMapper().readValue(response, Email[].class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public TextMessage[] sendStatementText(TextRequest textRequest) throws EntityException {
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
  public Letter sendStatementLetter(LetterRequest letterRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/letters";

    try {
      String letterRequestJson = letterRequest.toJsonString();

      String response = this.getConnection().post(url, null, letterRequestJson);

      return Util.getMapper().readValue(response, Letter.class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Invoice invoice() throws EntityException {
    String url = this.getEndpoint(true) + "/invoices";

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

  @JsonIgnore
  public Invoice consolidateInvoices() throws EntityException {
    Invoice invoice = this.consolidateInvoices(null);

    return invoice;
  }

  @JsonIgnore
  public Invoice consolidateInvoices(Long cutoffDate) throws EntityException {

    String url = this.getEndpoint(true) + "/consolidate_invoices";

    String cutoffJson = null;

    if (cutoffDate != null) {
      cutoffJson = "{\"cutoff_date\":" + String.valueOf(cutoffDate) + "}";
    } else {
      cutoffJson = "{}";
    }

    try {
      String response = this.getConnection().post(url, null, cutoffJson);

      Invoice invoice = Util.getMapper().readValue(response, Invoice.class);
      invoice.setConnection(this.getConnection());
      invoice.setClass(Invoice.class);

      return invoice;
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Contact newContact() {
    Contact contact = new Contact(this.getConnection());
    contact.setEndpointBase(this.getEndpoint(true));
    return contact;
  }

  @JsonIgnore
  public PendingLineItem newPendingLineItem() {
    PendingLineItem pli = new PendingLineItem(this.getConnection());
    pli.setEndpointBase(this.getEndpoint(true));
    return pli;
  }

  @JsonIgnore
  public Note newNote() {
    Note note = new Note(this.getConnection());
    note.setEndpointBase(this.getEndpoint(true));
    return note;
  }

  @JsonIgnore
  public Task newTask() {
    Task task = new Task(this.getConnection());
    task.customerId = this.id;
    return task;
  }

  @JsonIgnore
  public PaymentSource createPaymentSource(SourceRequest sourceRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/payment_sources";

    try {

      String sourceRequestJson = sourceRequest.toJsonString();
      String response = this.getConnection().post(url, null, sourceRequestJson);

      PaymentSource paymentSource = Util.getMapper().readValue(response, PaymentSource.class);

      // only need to set connection and base; setClass is called by constructors.
      paymentSource.setConnection(this.getConnection());
      paymentSource.setEndpointBase(this.getEndpoint(true));

      return paymentSource;
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public EntityList<PaymentSource> listPaymentSources() throws EntityException {
    PaymentSource source = new PaymentSource(this.getConnection());
    source.setEndpointBase(this.getEndpoint(true));
    return source.listAll();
  }

  public String[] getTaxes() {
    if (this.taxes == null) {
      return null;
    }
    String[] ids = new String[this.taxes.length];
    for (int i = 0; i < this.taxes.length; i++) {
      ids[i] = this.taxes[i].toString();
    }

    return ids;
  }
}
