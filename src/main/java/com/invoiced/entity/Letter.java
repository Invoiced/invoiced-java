package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Letter extends AbstractItem {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("id")
    public String id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("state")
    public String state;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("num_pages")
    public Long numPages;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("expected_delivery_date")
    public Long expectedDeliveryDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("to")
    public String to;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    public Long createdAt;
}
