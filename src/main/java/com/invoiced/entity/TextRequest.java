package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TextRequest extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("to")
	public TextRecipient[] to;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("message")
	public String message;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("type")
	public String type;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("start")
	public long start;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("end")
	public long end;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("items")
	public String items;

}