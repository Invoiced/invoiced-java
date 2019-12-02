package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFilter;
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

@JsonFilter("customFilter")
public class PaymentPlan extends AbstractEntity<PaymentPlan> {

	PaymentPlan(Connection conn, long invoiceId) {
		super(conn, PaymentPlan.class);
		this.setParentID(String.valueOf(invoiceId));
		this.setParentName("invoices");
		this.setEntityName();
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
	protected boolean idIsString() {
		return false;
	}

	@Override
	@JsonIgnore
	protected String getEntityIdString() throws EntityException {
		return String.valueOf(this.id);
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
	protected void setEntityName() {
		this.entityName = this.getParentName() + "/" + this.getParentID() + "/payment_plan";
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return true;
	}

	@Override
	@JsonIgnore
	protected String[] getCreateExclusions() {
		return new String[] {"id", "object", "status", "approval", "created_at"};
	}

	@Override
	@JsonIgnore
	protected String[] getSaveExclusions() {
		return new String[] {};
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
		
			String url = this.getConnection().baseUrl() + "/invoices/" + this.getParentID() + "/payment_plan";
	
			try {
	
				this.getConnection().delete(url);
	
			} catch (Throwable c) {
	
				throw new EntityException(c);
			}
		}

}