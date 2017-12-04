package com.invoiced.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionAddOn extends AbstractItem {

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("object")
	public String object;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("description")
	public String description;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("catalog_item")
	public String catalogItem;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("quantity")
	public long quantity;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("created_at")
	public long created_at;

}