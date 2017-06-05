package com.invoiced.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityList<T extends AbstractEntity> extends ArrayList<T> {

	private HashMap<String, String> linkURLs;
	private int totalCount;

	public HashMap<String, String> getLinkURLs() {
		return this.linkURLs;
	}

	public void setLinkURLs(HashMap<String, String> linkURLs) {
		this.linkURLs = linkURLs;

	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

}