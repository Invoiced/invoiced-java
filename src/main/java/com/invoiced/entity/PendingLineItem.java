package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PendingLineItem extends AbstractEntity<PendingLineItem> {

	private long customerId;

	PendingLineItem(Connection conn, long customerId) {

		super(conn, PendingLineItem.class);
		this.customerId = customerId;

	}

	PendingLineItem() {
		super(PendingLineItem.class);

	}

	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@JsonIgnore
	protected String getEntityName() {
		return "customers" + "/" + String.valueOf(this.customerId) + "/line_items";
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
		return true;
	}

	@JsonIgnore
	protected void setParentID(long parentID) {
		this.customerId = parentID;
	}

	@JsonIgnore
	protected long getParentID() {
		return this.customerId;
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("catalog_item")
	public String catalogItem;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("type")
	public String type;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("name")
	public String name;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("description")
	public String description;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("quantity")
	public double quantity;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("amount")
	public double amount;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("unit_cost")
	public double unitCost;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discountable")
	public boolean discountable;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discounts")
	public Discount[] discounts;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxable")
	public boolean taxable;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
	public Tax[] taxes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("plan")
	public String plan;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

}