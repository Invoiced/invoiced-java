package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailRequest extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("to")
	public EmailRecipient[] to;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("bcc")
	public String bcc;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("subject")
	public String subject;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("message")
	public String message;

}