package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;


public class SubscriptionAddOn extends AbstractItem {


	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	public long id;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("catalog_item")
	public String catalogItem;



	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("quantity")
	public long quantity;



	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("created_at")
	public Timestamp created_at;




}