package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Attachment extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("file")
  public File file;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("location")
  public String location;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;
}
