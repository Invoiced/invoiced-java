package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Installment extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("date")
	public long date;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("amount")
	public long amount;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("balance")
    public long balance;
        
}