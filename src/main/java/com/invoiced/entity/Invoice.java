package com.invoiced.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public class Invoice extends AbstractEntity<Invoice> {

	public Invoice(Connection conn) {
		super(conn, Invoice.class);
	}

	Invoice() {
		super(Invoice.class);
	}

	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@JsonIgnore
	protected String getEntityName() {
		return "invoices";
	}

	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

	@JsonIgnore
	protected boolean hasList() {
		return true;
	}

	@JsonIgnore
	protected boolean isSubEntity() {
		return false;
	}

	@JsonIgnore
	protected void setParentID(long parentID) {

	}

	@JsonIgnore
	protected long getParentID() {
		return -1;
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("customer")
	public long customer;

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
	@JsonProperty("currency")
	public String currency;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("draft")
	public boolean draft;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("closed")
	public boolean closed;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("paid")
	public boolean paid;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("status")
	public String status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("sent")
	public boolean sent;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("chase")
	public boolean chase;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("next_chase_on")
	public Timestamp nextChaseOn;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("attempt_count")
	public long attemptCount;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("next_payment_attempt")
	public Timestamp nextPaymentAttempt;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("subscription")
	public long subscription;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("date")
	public Timestamp date;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("due_date")
	public Timestamp dueDate;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("payment_terms")
	public String paymentTerms;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("items")
	public LineItem[] items;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("notes")
	public String notes;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("subtotal")
	public double subtotal;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discounts")
	public Discount[] discounts;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public Tax[] taxes;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("total")
	public double total;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("balance")
	public double balance;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("tags")
	public Object[] tags;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("url")
	public String url;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("payment_url")
	public String paymentUrl;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("pdf_url")
	public String pdfUrl;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

	@JsonIgnore
	public Email[] send(EmailRequest emailRequest) throws EntityException {

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
	public void pay() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
				+ String.valueOf(this.getEntityId()) + "/pay";

		try {

			String response = this.getConnection().post(url, null, "");

			Invoice v1 = Util.getMapper().readValue(response, Invoice.class);

			// v1.setConnection(this.conn);
			// v1.setClass(Invoice.class);

			setFields(v1, this);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return;

	}

	@JsonIgnore
	public Attachment[] listAttachments() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
				+ String.valueOf(this.getEntityId()) + "/attachments";

		Attachment[] attachments = null;

		try {

			String response = this.getConnection().post(url, null, "");

			attachments = Util.getMapper().readValue(response, Attachment[].class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return attachments;
	}

}
