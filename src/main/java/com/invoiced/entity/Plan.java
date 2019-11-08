package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Plan extends AbstractEntity<Plan> {

	public Plan(Connection conn) {
		super(conn, Plan.class);
	}

	Plan() {
		super(Plan.class);
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
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "plans";
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

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("object")
	public String object;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("catalog_item")
	public String catalogItem;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("name")
	public String name;
    
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("currency")
    public String currency;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("amount")
    public Long amount;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("pricing_mode")
    public String pricingMode;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("quantity_type")
    public String quantityType;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("interval")
    public String interval;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("interval_count")
    public Long intervalCount;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("tiers")
	public Object[] tiers;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

}
