package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Event extends AbstractEntity<Event> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("type")
    public String type;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("timestamp")
    public long timestamp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("data")
    public Object data;

    public Event(Connection conn) {
        super(conn, Event.class);
        this.entityName = "/events";
    }

    Event() {
        super(Event.class);
        this.entityName = "/events";
    }

    @Override
    @JsonIgnore
    protected boolean hasCRUD() {
        return false;
    }

    @Override
    @JsonIgnore
    protected String getEntityId() {
        return String.valueOf(this.id);
    }

    @Override
    @JsonIgnore
    protected String[] getCreateExclusions() {
        return new String[]{};
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{};
    }
}
