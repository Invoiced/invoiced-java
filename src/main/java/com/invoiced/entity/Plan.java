package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

@JsonFilter("customFilter")
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
	protected boolean idIsString() {
		return true;
	}

	@Override
	@JsonIgnore
	protected String getEntityIdString() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
		return true;
	}

	@Override
	@JsonIgnore
	protected long getEntityId() throws EntityException {
		throw new EntityException(new Throwable());
	}

	@Override
	@JsonIgnore
	protected void setEntityName() {
		this.entityName = "plans";
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return false;
	}

	@Override
	@JsonIgnore
	protected String[] getCreateExclusions() {
		return new String[] {"object", "created_at"};
	}

	@Override
	@JsonIgnore
	protected String[] getSaveExclusions() {
		return new String[] {"id", "object", "catalog_item", "currency", "amount", "pricing_mode", "quantity_type", "interval", "interval_count", "tiers", "created_at"};
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public String id;

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
