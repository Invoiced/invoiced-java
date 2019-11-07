package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

//@JsonIgnoreProperties(value = { "paid" }, allowSetters = true)
public class Invoice extends AbstractEntity<Invoice> {

	public Invoice(Connection conn) {
		super(conn, Invoice.class);
	}

	Invoice() {
		super(Invoice.class);
	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "invoices";
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
	@JsonProperty("autopay")
	public Boolean autopay;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("currency")
	public String currency;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("draft")
	public Boolean draft;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("closed")
	public Boolean closed;

	@JsonProperty(value = "paid", access = JsonProperty.Access.WRITE_ONLY)
	public Boolean paid;

	@JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
	public String status;

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

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("date")
	public long date;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("due_date")
	public long dueDate;

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

	@JsonProperty(value = "total", access = JsonProperty.Access.WRITE_ONLY)
	public double total;

	@JsonProperty(value = "balance", access = JsonProperty.Access.WRITE_ONLY)
	public double balance;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("tags")
	public Object[] tags;

	@JsonProperty(value = "url", access = JsonProperty.Access.WRITE_ONLY)
	public String url;

	@JsonProperty(value = "payment_url", access = JsonProperty.Access.WRITE_ONLY)
	public String paymentUrl;

	@JsonProperty(value = "pdf_url", access = JsonProperty.Access.WRITE_ONLY)
	public String pdfUrl;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
	public long createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("ship_to")
	public Object shipTo;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("attachments")
	public long[] attachments;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("disabled_payment_methods")
	public String[] disabledPaymentMethods;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("calculate_taxes")
	public String calculateTaxes;

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
	public TextMessage[] sendText(TextRequest textRequest) throws EntityException {

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
	public Letter[] sendLetter() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/letters";

		Letter[] letters = null;

		try {

			String response = this.getConnection().post(url, null, "{}");

			letters = Util.getMapper().readValue(response, Letter[].class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return letters;
	}

	@JsonIgnore
	public void pay() throws EntityException {

		String url = this.getConnection().baseUrl() + "/" + this.getEntityName() + "/"
		             + String.valueOf(this.getEntityId()) + "/pay";

		try {

			String response = this.getConnection().post(url, null, "");

			Invoice v1 = Util.getMapper().readValue(response, Invoice.class);

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

	public void void() throws EntityException {

		String url = this.conn.baseUrl() + "/" + this.getEntityName() + "/" + String.valueOf(this.getEntityId()) + "/void";
		
		T v1 = null;

		try {
			String response = this.conn.post(url, null, "{}"));

			v1 = Util.getMapper().readValue(response, this.tClass);

			setFields(v1, this);

		} catch (Throwable c) {
			throw new EntityException(c);
		}
	}

	@JsonIgnore
	public Contact newNote() {
		return new Note(this.getConnection(), null, this.id);
	}

}
