package com.invoiced.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Event extends AbstractEntity<Event> {

	public Event(Connection conn) {

		super(conn, Event.class);

	}

	Event() {
		super(Event.class);

	}

	@JsonIgnore
	protected boolean hasCRUD() {
		return false;
	}

	@JsonIgnore
	protected boolean hasList() {
		return true;
	}

	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@JsonIgnore
	protected String getEntityName() {
		return "events";
	}

	@JsonIgnore
	protected boolean isSubEntity() {
		return false;
	}

	@JsonIgnore
	protected void setParentID(long parentID) {

	}

	@JsonIgnore
	protected long getParentID() {
		return -1;
	}

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("type")
	public String type;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("timestamp")
	public Timestamp timestamp;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("data")
	public Object data;

}
