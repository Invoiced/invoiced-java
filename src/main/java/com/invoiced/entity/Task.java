package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task extends AbstractEntity<Task> {

	public Task(Connection conn) {
		super(conn, Task.class);
	}

	public Task(Connection conn, long customerId) {
		super(conn, Task.class);
		this.customerId = customerId;
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
	protected boolean idIsString() {
		return false;
	}

	@Override
	@JsonIgnore
	protected String getEntityIdString() {
		return null;
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
    public Long customerId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("user_id")
    public Long userId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("due_date")
    public Long dueDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("complete")
    public Boolean complete;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("completed_date")
    public Long completedDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("completed_by_user_id")
    public Long completedByUserId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("chase_step_id")
    public Long chaseStepId;

	@JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public long createdAt;
    
}