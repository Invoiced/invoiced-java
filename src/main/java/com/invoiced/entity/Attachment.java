package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;

public class Attachment extends AbstractItem {


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("id")
	public long id;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("file")
	public File file;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;
}

