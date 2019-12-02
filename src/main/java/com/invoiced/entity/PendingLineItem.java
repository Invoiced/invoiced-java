package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

@JsonFilter("customFilter")
public class PendingLineItem extends AbstractEntity<PendingLineItem> {

	PendingLineItem(Connection conn, long customerId) {
		super(conn, PendingLineItem.class);
		this.setParentName("customers");
		this.setParentID(String.valueOf(customerId));
		this.setEntityName();
	}

	PendingLineItem() {
		super(PendingLineItem.class);
	}

	@Override
	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected void setEntityName() {
		this.entityName = this.getParentName() + "/" + this.getParentID() + "/line_items";
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
		return true;
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return true;
	}

	@Override
	@JsonIgnore
	protected String[] getCreateExclusions() {
		return new String[] {"id", "plan"};
	}

	@Override
	@JsonIgnore
	protected String[] getSaveExclusions() {
		return new String[] {"id", "plan", "catalog_item"};
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
	public Boolean discountable;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discounts")
	public Discount[] discounts;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxable")
	public Boolean taxable;

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