package com.invoiced.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Discount extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("amount")
	public double amount;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("coupon")
	public Rate coupon;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("expires")
	public long expires;

}
