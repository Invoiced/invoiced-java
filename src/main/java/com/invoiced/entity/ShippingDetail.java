package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ShippingDetail extends AbstractItem {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("name")
    public String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("attention_to")
    public String attentionTo;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("address1")
    public String address1;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("address2")
    public String address2;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("city")
    public String city;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("state")
    public String state;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("postal_code")
    public String postalCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("country")
    public String country;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;
}
