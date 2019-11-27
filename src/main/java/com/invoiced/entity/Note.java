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
		this.entityName = "/notes";
	}

	Note() {
		super(Note.class);
		this.entityName = "/notes";
	}

	@Override
	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

    @Override
	@JsonIgnore
	protected String getEntityId() {
		return String.valueOf(this.id);
	}

	@Override
	@JsonIgnore
	protected boolean hasList() {
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

	@Override
	public void create() throws EntityException {
		String endpointBase = this.getEndpointBase();
		this.setEndpointBase("");
		super.create();
		this.setEndpointBase(endpointBase);
	}

	@Override
	public void save() throws EntityException {
		String endpointBase = this.getEndpointBase();
		this.setEndpointBase("");
		super.save();
		this.setEndpointBase(endpointBase);
	}

	@Override
	public void delete() throws EntityException {
		String endpointBase = this.getEndpointBase();
		this.setEndpointBase("");
		super.delete();
		this.setEndpointBase(endpointBase);
	}
}