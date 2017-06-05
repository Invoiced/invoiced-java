package com.invoiced.entity;

import java.sql.Timestamp;

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
	@JsonProperty("collection_mode")
	public String collectionMode;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("payment_terms")
	public String paymentTerms;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("payment_source")
	public PaymentSource paymentSource;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public Tax[] taxes;

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
	@JsonProperty("tax_id")
	public String taxId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("phone")
	public String phone;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("notes")
	public String notes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("statement_pdf_url")
	public String statementPdfUrl;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;

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
	public Invoice invoice() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
				+ String.valueOf(this.getEntityId()) + "/invoices";

		Invoice invoice = null;

		try {

			String response = this.getConnection().post(url, null, "");

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

}
