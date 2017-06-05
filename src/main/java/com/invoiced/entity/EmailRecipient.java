package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class EmailRecipient extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("name")
	public String name;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("email")
	public String email;

}