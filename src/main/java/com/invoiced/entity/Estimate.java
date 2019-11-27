package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public class Estimate extends AbstractEntity<Estimate> {

	public Estimate(Connection conn) {
		super(conn, Estimate.class);
		this.entityName = "/estimates";
	}

	Estimate() {
		super(Estimate.class);
		this.entityName = "/estimates";
	}

	@Override
	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

    @Override
	@JsonIgnore
	protected String getEntityId() {
		return String.valueOf(this.id);
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
		return true;
	}

	@Override
	@JsonIgnore
	protected String[] getCreateExclusions() {
		return new String[] {"id", "approved", "status", "subtotal", "total", "url", "pdf_url", "object", "created_at"};
	}

	@Override
	@JsonIgnore
	protected String[] getSaveExclusions() {
		return new String[] {"id", "approved", "status", "subtotal", "total", "url", "pdf_url", "object", "created_at", "invoice", "customer"};
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
	@JsonProperty("invoice")
	public String invoice;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("number")
	public String number;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("email")
	public String email;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("currency")
	public String currency;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("draft")
	public Boolean draft;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("closed")
	public Boolean closed;

	@JsonProperty(value = "approved", access = JsonProperty.Access.WRITE_ONLY)
	public Boolean approved;

	@JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
	public String status;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("date")
	public long date;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("expiration_date")
	public long expirationDate;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("deposit")
	public long deposit;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("deposit_paid")
	public Boolean depositPaid;

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
	@JsonProperty("disabled_payment_methods")
	public String[] disabledPaymentMethods;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public Tax[] taxes;

	@JsonProperty(value = "total", access = JsonProperty.Access.WRITE_ONLY)
	public double total;

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
	@JsonProperty("ship_to")
    public Object shipTo;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("calculate_taxes")
	public Boolean calculateTaxes;

	@JsonIgnore
	public Email[] send(EmailRequest emailRequest) throws EntityException {

		String url = this.getEndpoint(true, true) + "/emails";

		Email[] emails;

		try {

			String emailRequestJson = emailRequest.toJsonString();

			String response = this.getConnection().post(url, null, emailRequestJson);

			emails = Util.getMapper().readValue(response, Email[].class);

		} catch (Throwable c) {

			throw new EntityException(c);
		}

		return emails;
	}

	public void voidEstimate() throws EntityException {

		String url = this.getEndpoint(true, true) + "/void";
		
		Estimate v1;

		try {
			String response = this.getConnection().post(url, null, "{}");

			v1 = Util.getMapper().readValue(response, Estimate.class);

			setFields(v1, this);

		} catch (Throwable c) {
			throw new EntityException(c);
		}
	}

	@JsonIgnore
	public Invoice invoice() throws EntityException {

		String url = this.getEndpoint(true, true) + "/invoice";
		
		Invoice invoice;

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
}