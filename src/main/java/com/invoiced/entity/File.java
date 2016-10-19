package com.invoiced.entity;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class File extends AbstractEntity<File> {

	public File(Connection conn) {

		super(conn, File.class);

	}

	File() {
		super(File.class);

	}

	@JsonIgnore
	protected boolean hasCRUD() {
		return true;
	}

	@JsonIgnore
	protected boolean hasList() {
		return false;
	}


	@JsonIgnore
	protected long getEntityId() {
		return this.id;
	}

	@JsonIgnore
	protected String getEntityName() {
		return "files";
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


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;




}
