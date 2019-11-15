package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tax extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("amount")
	public double amount;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("tax_rate")
	public TaxRate taxRate;

}