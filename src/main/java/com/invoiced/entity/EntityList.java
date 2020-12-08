package com.invoiced.entity;

import java.util.ArrayList;
import java.util.HashMap;

public final class EntityList<T extends AbstractEntity> extends ArrayList<T> {

  private static final long serialVersionUID = 1L;

  private HashMap<String, String> linkURLs;
  private int totalCount;

  public HashMap<String, String> getLinkURLs() {
    return this.linkURLs;
  }

  public void setLinkURLs(HashMap<String, String> linkURLs) {
    this.linkURLs = linkURLs;
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
