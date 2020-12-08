package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("customFilter")
public final class File extends AbstractEntity<File> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("id")
    public long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("name")
    public String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("object")
    public String object;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("size")
    public long size;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("type")
    public String type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("url")
    public String url;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("created_at")
    public long createdAt;

    public File(Connection conn) {
        super(conn, File.class);
        this.entityName = "/files";
    }

    File() {
        super(File.class);
        this.entityName = "/files";
    }

    @Override
    @JsonIgnore
    protected boolean hasList() {
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
        return new String[]{"id", "object", "created_at"};
    }

    @Override
    @JsonIgnore
    protected String[] getSaveExclusions() {
        return new String[]{};
    }
}
