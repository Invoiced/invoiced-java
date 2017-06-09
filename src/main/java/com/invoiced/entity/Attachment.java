package com.invoiced.entity;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attachment extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("file")
	public File file;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("created_at")
	public long createdAt;
}
