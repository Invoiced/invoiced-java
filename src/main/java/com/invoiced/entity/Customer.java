package com.invoiced.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public class Customer extends AbstractEntity<Customer> {

	public Customer(Connection conn) {
		super(conn, Customer.class);
	}

	Customer() {
		super(Customer.class);
	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "customers";
	}

	@Override
	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
		return true;
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return false;
	}

	@Override
	@JsonIgnore
	protected void setParentID(long parentID) {

	}

	@Override
	@JsonIgnore
	protected long getParentID() {
		return -1;
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

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
	public long autopayDelayDays;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("payment_terms")
	public String paymentTerms;

	@JsonProperty(value = "payment_source", access = JsonProperty.Access.WRITE_ONLY)
	public PaymentSource paymentSource;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public Tax[] taxes;

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
	@JsonProperty("stripe_token")
	public String stripeToken;

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
	public long chasingCadence;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("next_chase_step")
	public long nextChaseStep;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("tax_id")
	public String taxId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("phone")
	public String phone;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("credit_hold")
	public Boolean creditHold;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("credit_limit")
	public long creditLimit;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("owner")
	public long owner;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxable")
	public Boolean taxable;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("notes")
	public String notes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("avalara_entity_use_code")
	public String avalaraEntityUseCode;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("avalara_exemption_number")
	public String avalaraExemptionNumber;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("parent_customer")
	public long parentCustomer;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("sign_up_page")
	public long signUpPage;

	@JsonProperty(value = "sign_up_url", access = JsonProperty.Access.WRITE_ONLY)
	public String signUpUrl;

	@JsonProperty(value = "statement_pdf_url", access = JsonProperty.Access.WRITE_ONLY)
	public String statementPdfUrl;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
	public long createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

	@JsonIgnore
	public Balance getBalance() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/balance";

		Balance balance = null;

		try {

			String response = this.getConnection().get(url, null);

			balance = Util.getMapper().readValue(response, Balance.class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return balance;
	}

	@JsonIgnore
	public Email[] sendStatement(EmailRequest emailRequest) throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/emails";

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
	public TextMessage[] sendStatementText(TextRequest textRequest) throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/text_messages";

		TextMessage[] textMessages = null;

		try {

			String textRequestJson = textRequest.toJsonString();

			String response = this.getConnection().post(url, null, textRequestJson);

			textMessages = Util.getMapper().readValue(response, TextMessage[].class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return textMessages;
	}

	@JsonIgnore
	public Letter[] sendStatementLetter(LetterRequest letterRequest) throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/letters";

		Letter[] letters = null;

		try {

			String letterRequestJson = letterRequest.toJsonString();

			String response = this.getConnection().post(url, null, letterRequestJson);

			letters = Util.getMapper().readValue(response, Letter[].class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return letters;
	}

	@JsonIgnore
	public Invoice invoice() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/invoices";

		Invoice invoice = null;

		try {

			String response = this.getConnection().post(url, null, "{}");

			invoice = Util.getMapper().readValue(response, Invoice.class);
			invoice.setConnection(this.getConnection());
			invoice.setClass(Invoice.class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return invoice;
	}

	@JsonIgnore
	public Invoice consolidateInvoices() throws EntityException {
		Invoice invoice = this.consolidateInvoices(null);

		return invoice;
	}

	@JsonIgnore
	public Invoice consolidateInvoices(Long cutoffDate) throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(this.getEntityId()) + "/consolidate_invoices";

		String cutoffJson = null;

		if (cutoffDate != null) {
			cutoffJson = "{\"cutoff_date\":" + String.valueOf(cutoffDate) + "}";
		} else {
			cutoffJson = "{}";
		}

		Invoice invoice = null;

		try {

			String response = this.getConnection().post(url, null, cutoffJson);

			invoice = Util.getMapper().readValue(response, Invoice.class);
			invoice.setConnection(this.getConnection());
			invoice.setClass(Invoice.class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return invoice;
	}

	@JsonIgnore
	public Contact newContact() {
		return new Contact(this.getConnection(), this.id);
	}

	@JsonIgnore
	public PendingLineItem newPendingLineItem() {
		return new PendingLineItem(this.getConnection(), this.id);
	}

	@JsonIgnore
	public Note newNote() {
		return new Note(this.getConnection(), this.id, 0);
	}

}

