package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TextMessage extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("id")
  public String id;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("state")
  public String state;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("to")
  public String to;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("message")
  public String message;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;
}
