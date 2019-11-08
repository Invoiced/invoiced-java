package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task extends AbstractEntity<Task> {

	public Task(Connection conn) {
		super(conn, Task.class);
	}

	Task() {
		super(Task.class);
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
		return "tasks";
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
	@JsonProperty("name")
	public String name;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("action")
	public String action;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("customer_id")
    public long customerId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("user_id")
    public long userId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("due_date")
    public long dueDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("complete")
    public Boolean complete;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("completed_date")
    public long completedDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("completed_by_user_id")
    public long completedByUserId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("chase_step_id")
    public long chaseStepId;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;
    
}