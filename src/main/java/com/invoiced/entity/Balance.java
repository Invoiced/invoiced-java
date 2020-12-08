package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Balance extends AbstractItem {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("currency")
    public String currency;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("available_credits")
    public Double availableCredits;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("history")
    public BalanceHistory[] history;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("past_due")
    public Boolean pastDue;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("total_outstanding")
    public Double totalOutstanding;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("due_now")
    public Double dueNow;
}
