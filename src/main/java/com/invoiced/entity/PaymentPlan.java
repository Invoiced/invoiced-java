package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.invoiced.exception.EntityException;

import javax.swing.text.html.parser.Entity;

@JsonFilter("customFilter")
public class PaymentPlan extends AbstractEntity<PaymentPlan> {

	PaymentPlan(Connection conn) {
		super(conn, PaymentPlan.class);
		this.entityName = "/payment_plan";
	}

	PaymentPlan() {
		super(PaymentPlan.class);
		this.entityName = "/payment_plan";
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
		return false;
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
	public Long id;

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

	@Override
	public void delete() throws EntityException {
		try {
			super.delete(false);
		} catch (Throwable c) {
			throw new EntityException(c);
		}
	}

	public void cancel() throws EntityException {

			try {
	
				this.delete();
	
			} catch (Throwable c) {
	
				throw new EntityException(c);
			}
		}

}