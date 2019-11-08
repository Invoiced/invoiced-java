package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class SubscriptionPreview extends AbstractEntity<SubscriptionPreview> {

	public SubscriptionPreview(Connection conn) {
		super(conn, SubscriptionPreview.class);

	}

	SubscriptionPreview() {
		super(SubscriptionPreview.class);

	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return -1;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "subscriptions/preview";
	}

	@Override
	@JsonIgnore
	protected boolean hasCRUD() {
		return false;
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
		return false;
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

	@JsonProperty(value = "first_invoice", access = JsonProperty.Access.WRITE_ONLY)
	public Invoice firstInvoice;

	@JsonProperty(value = "mrr", access = JsonProperty.Access.WRITE_ONLY)
    public long mrr;
    
	@JsonProperty(value = "recurring_total", access = JsonProperty.Access.WRITE_ONLY)
	public long recurring_total;
}