package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

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