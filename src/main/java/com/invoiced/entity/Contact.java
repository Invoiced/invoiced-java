package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;


public class Contact extends AbstractEntity<Contact> {

	private long customerId;

	Contact(Connection conn, long customerId ) {

		super(conn, Contact.class);
		this.customerId = customerId;

	}

	Contact() {
		super(Contact.class);

	}

	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@JsonIgnore
	protected String getEntityName() {
		return "customers" + "/" + String.valueOf(this.customerId) + "/contacts";
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
	@JsonProperty("name")
	public String name;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("email")
	public String email;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("primary")
	public boolean primary;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("address1")
	public String address1;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("address2")
	public String address2;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("city")
	public String city;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("state")
	public String state;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("postal_code")
	public String postalCode;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("country")
	public String country;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;





}