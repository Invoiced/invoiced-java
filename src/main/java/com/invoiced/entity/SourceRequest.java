package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SourceRequest extends AbstractItem {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("method")
  public String method;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("make_default")
  public Boolean makeDefault;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("invoiced_token")
  public String invoicedToken;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("gateway_token")
  public String gatewayToken;
}
