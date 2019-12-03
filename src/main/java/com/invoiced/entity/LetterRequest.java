package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LetterRequest extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("type")
  public String type;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("start")
  public Long start;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("end")
  public Long end;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("items")
  public String items;
}
