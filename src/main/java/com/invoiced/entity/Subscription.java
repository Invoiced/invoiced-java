package com.invoiced.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class Subscription extends AbstractEntity<Subscription> {

	public Subscription(Connection conn) {
		super(conn, Subscription.class);
	}

	Subscription() {
		super(Subscription.class);
	}

	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@JsonIgnore
	protected String getEntityName() {
		return "subscriptions";
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
	@JsonProperty("plan")
	public String plan;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("start_date")
	public Timestamp startDate;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("quantity")
	public int quantity;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("cycles")
	public int cycles;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("period_start")
	public Timestamp periodStart;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("period_end")
	public Timestamp periodEnd;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("cancel_at_period_end")
	public boolean cancelAtPeriodEnd;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("canceled_at")
	public Timestamp canceledAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("status")
	public String status;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("addons")
	public SubscriptionAddOn[] addons;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discounts")
	public String[] discounts;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public String[] taxes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("url")
	public String url;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

	public void cancel() throws EntityException {

		try {

			this.delete();

		} catch (Throwable c) {

			throw new EntityException(c);
		}

	}

}
