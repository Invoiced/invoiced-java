package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

@JsonFilter("customFilter")
public class Note extends AbstractEntity<Note> {

	public Note(Connection conn) {
		super(conn, Note.class);
	}

	public Note(Connection conn, long customerId, long invoiceId) {
		super(conn, Note.class);
		if (invoiceId > 0) {
			this.setParentName("invoices");
			this.setParentID(String.valueOf(invoiceId));
		}
		else if (customerId > 0) {
			this.setParentName("customers");
			this.setParentID(String.valueOf(customerId));
		}
		setListUrl();
	}

	Note() {
		super(Note.class);
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
	protected long getEntityId() {
		return this.id;
	}

	@Override
	@JsonIgnore
	protected void setEntityName() {
		this.entityName = "notes";
	}

	@Override
	@JsonIgnore
	protected boolean isSubEntity() {
		return true;
	}

	@Override
	@JsonIgnore
	protected String[] getCreateExclusions() {
		return new String[] {"id", "object", "created_at"};
	}

	@Override
	@JsonIgnore
	protected String[] getSaveExclusions() {
		return new String[] {"id", "object", "customer", "customer_id", "invoice_id", "created_at"};
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("object")
	public String object;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("notes")
	public String notes;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("customer")
	public Long customer;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("customer_id")
	public Long customerId;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("invoice_id")
    public Long invoiceId;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("metadata")
	public Object metadata;

}