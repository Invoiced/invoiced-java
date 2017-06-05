package com.invoiced.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Email extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("id")
	public String id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("state")
	public String state;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("reject_reason")
	public String rejectReason;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("email")
	public String email;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("template")
	public String template;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("subject")
	public String subject;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("message")
	public String message;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("opens")
	public int opens;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("opens_detail")
	public Object opensDetail;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("clicks")
	public int clicks;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("clicks_detail")
	public Object clicksDetails;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("created_at")
	public Timestamp createdAt;

	// "opens": 0,
	// "opens_detail": [],
	// "clicks": 0,
	// "clicks_detail": [],
	// "created_at": 1436890047

}