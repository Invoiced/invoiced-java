package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;

@JsonFilter("customFilter")
public final class Note extends AbstractEntity<Note> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public Long id;

    @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
    public String object;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("notes")
    public String notes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("customer")
    public Long customer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("user")
    public Object user;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("metadata")
    public Object metadata;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;

    @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long updatedAt;

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
    protected String getEntityId() {
        return String.valueOf(this.id);
    }

    @Override
    @JsonIgnore
    protected String[] getCreateExclusions() {
        return new String[]{"id", "object", "created_at"};
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{"id", "object", "customer", "customer_id", "invoice_id", "created_at"};
    }

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
