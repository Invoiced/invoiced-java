package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;


public class CreditNote extends AbstractEntity<CreditNote> {

	public CreditNote(Connection conn) {
		super(conn, CreditNote.class);
	}

	CreditNote() {
		super(CreditNote.class);
	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "credit_notes";
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

	@JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
	public String object;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("customer")
	public long customer;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("invoice")
	public long invoice;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("name")
	public String name;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("number")
	public String number;

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

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("date")
	public long date;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("items")
	public LineItem[] items;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("notes")
	public String notes;

	@JsonProperty(value = "subtotal", access = JsonProperty.Access.WRITE_ONLY)
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

	@JsonProperty(value = "url", access = JsonProperty.Access.WRITE_ONLY)
	public String url;

	@JsonProperty(value = "pdf_url", access = JsonProperty.Access.WRITE_ONLY)
	public String pdfUrl;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
	public long createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("attachments")
	public long[] attachments;

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
