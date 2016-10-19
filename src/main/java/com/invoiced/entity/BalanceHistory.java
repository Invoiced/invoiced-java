package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;


public class BalanceHistory extends AbstractItem {



	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("timestamp")
	public Timestamp timestamp;



	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("balance")
	public double balance;




}

