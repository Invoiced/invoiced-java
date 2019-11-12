package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;
import com.invoiced.exception.ApiException;
import com.invoiced.exception.AuthException;
import com.invoiced.exception.ConnException;
import com.invoiced.exception.InvalidRequestException;
import com.invoiced.exception.InvoicedException;
import com.invoiced.exception.RateLimitException;
import com.invoiced.util.ListResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.options.Options;

public class PaymentPlan extends AbstractEntity<PaymentPlan> {

	protected long invoiceID;

	PaymentPlan(Connection conn, long invoiceId) {
		super(conn, PaymentPlan.class);
		this.invoiceID = invoiceId;
	}

	PaymentPlan() {
		super(PaymentPlan.class);
	}

	@Override
	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
		return false;
	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "invoices" + "/" + String.valueOf(this.invoiceID) + "/payment_plan";
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return true;
	}

	@Override
	@JsonIgnore
	protected void setParentID(long parentID) {
		this.invoiceID = parentID;
	}

	@Override
	@JsonIgnore
	protected long getParentID() {
		return this.invoiceID;
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("object")
	public String object;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("status")
	public String status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("installments")
	public Installment[] installments;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("approval")
	public Object approval;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
	public long createdAt;

	public void cancel() throws EntityException {
		
			String url = this.conn.baseUrl() + "/invoices/" + String.valueOf(this.invoiceID) + "/payment_plan";
	
			try {
	
				this.conn.delete(url);
	
			} catch (Throwable c) {
	
				throw new EntityException(c);
			}
		}

}