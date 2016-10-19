package com.invoiced.entity;

import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.invoiced.util.Util;
import java.util.HashMap;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;


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


	public  String toJsonString() {


		String s = "AbstractEntityList";

		try {
			s = Util.getMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(this);

		} catch (JsonGenerationException e) {
			e.printStackTrace();

		} catch (JsonMappingException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}



		return s;
	}





}