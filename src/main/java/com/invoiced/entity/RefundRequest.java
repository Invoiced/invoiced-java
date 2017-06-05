package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

public class RefundRequest extends AbstractItem {

	RefundRequest(double amount) {
		this.amount = amount;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("amount")
	public double amount;

	// @JsonIgnore
	// public String toJsonString() throws EntityException {

	// String s = "Entity";

	// try {

	// s = Util.getMapper().writeValueAsString(this);

	// } catch (Throwable c) {
	// throw new EntityException(c);
	// }

	// return s;
	// }

}