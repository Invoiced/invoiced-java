package com.invoiced.entity;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.invoiced.exception.*;
import com.invoiced.util.Util;



public abstract class AbstractItem {


	@Override
	public String toString() {

		String s1 = super.toString();

		try {

			String jsonString = this.toJsonString();
			s1 = s1 + " JSON: " + jsonString;


		} catch (Throwable c) {
			throw new RuntimeException(c);
		}

		return s1;

	}


	public  String toJsonString() throws EntityException {


		String s = "AbstractItem";

		try {

			s = Util.getMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(this);


		} catch (Throwable c) {
			throw new EntityException(c);
		}


		return s;
	}




}