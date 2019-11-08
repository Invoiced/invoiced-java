package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CatalogItem extends AbstractEntity<CatalogItem> {

	public CatalogItem(Connection conn) {
		super(conn, CatalogItem.class);
	}

	CatalogItem() {
		super(CatalogItem.class);
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
		return "catalog_items";
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

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("name")
	public String name;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("currency")
    public String currency;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("unit_cost")
    public Long unitCost;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("description")
    public String description;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("type")
    public String type;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxable")
    public Boolean taxable;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("taxes")
    public Tax[] taxes;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("avalara_tax_code")
    public String avalaraTaxCode;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("gl_account")
    public String glAccount;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("discountable")
    public Boolean discountable;
    
	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

}
