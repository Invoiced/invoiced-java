package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class Coupon extends AbstractEntity<Coupon> {

	public Coupon(Connection conn) {
		super(conn, Coupon.class);
	}

	Coupon() {
		super(Coupon.class);
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
	protected long getEntityId() throws EntityException {
		throw new EntityException(new Throwable());
	}

	@Override
	@JsonIgnore
	protected String getEntityName() {
		return "coupons";
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
	public String id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("object")
	public String object;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("name")
	public String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("currency")
    public String currency;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("value")
    public Long value;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("is_percent")
    public Boolean isPercent;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("exclusive")
    public Boolean exclusive;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("expiration_date")
    public Long expirationDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("max_redemptions")
    public Long maxRedemptions;
    
	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

}
