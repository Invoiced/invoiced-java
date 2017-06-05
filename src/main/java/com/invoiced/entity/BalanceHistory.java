package com.invoiced.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BalanceHistory extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("timestamp")
	public Timestamp timestamp;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("balance")
	public double balance;

}
