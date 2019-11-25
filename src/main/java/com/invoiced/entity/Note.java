package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

public class Note extends AbstractEntity<Note> {

	public Note(Connection conn) {
		super(conn, Note.class);
	}

	public Note(Connection conn, long customerId, long invoiceId) {
		super(conn, Note.class);
		if (invoiceId > 0) {
			this.invoiceId = invoiceId;
		}
		else if (customerId > 0) {
			this.customerId = customerId;
		}
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
	protected String getEntityName() {
		return "notes";
	}

	@Override
	@JsonIgnore
	protected String getListUrl() {
		if (this.invoiceId != null) return "invoices/" + String.valueOf(this.invoiceId) + "/notes";
		else if (this.customerId != null) return "customers/" + String.valueOf(this.customerId) + "/notes";
		else return "notes";
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