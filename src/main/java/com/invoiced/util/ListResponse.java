package com.invoiced.util;

import java.util.HashMap;

public class ListResponse {

  private String result;
  private HashMap<String, String> links;
  private int totalCount;

  public ListResponse(String result, HashMap<String, String> links, int totalCount) {
    this.result = result;
    this.links = links;
    this.totalCount = totalCount;
  }

  public String getResult() {
    return this.result;
  }

  public HashMap<String, String> getLinks() {
    return this.links;
  }

  public int getTotalCount() {
    return this.totalCount;
  }
}
