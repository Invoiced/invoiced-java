package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Rate extends AbstractItem {


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("id")
	public String id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("is_percent")
	public boolean isPercent;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("name")
	public String name;


	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("value")
	public double value;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("object")
	public String object;


}