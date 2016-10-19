package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;

public class Discount extends AbstractItem {


	Discount() {

	}


	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("amount")
	public double amount;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("coupon")
	public Rate coupon;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("expires")
	public Timestamp expires;


}

