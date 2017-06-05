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