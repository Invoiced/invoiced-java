package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefundRequest extends AbstractItem {

	RefundRequest(double amount) {
		this.amount = amount;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("amount")
	public double amount;

}