package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class Task extends AbstractEntity<Task> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public Long id;

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
    public Long createdAt;

    public Task(Connection conn) {
        super(conn, Task.class);
        this.entityName = "/tasks";
    }

    Task() {
        super(Task.class);
        this.entityName = "/tasks";
    }

    @Override
    @JsonIgnore
    protected String getEntityId() {
        return String.valueOf(this.id);
    }

    @Override
    @JsonIgnore
    protected String[] getCreateExclusions() {
        return new String[]{
                "id", "complete", "completed_date", "completed_by_user_id", "chase_step_id", "created_at"
        };
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{
                "id",
                "customer_id",
                "complete",
                "completed_date",
                "completed_by_user_id",
                "chase_step_id",
                "created_at"
        };
    }
}
