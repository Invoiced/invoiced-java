package com.invoiced.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.util.HashMap;
import java.net.URLDecoder;

public class Util {

	private static final ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public static final ObjectMapper getMapper() {
		return mapper;
	}

	public static final boolean jsonEqual(String s1, String s2) throws IOException {

		boolean isEqual = false;

		try {

			JsonNode rootNode = mapper.readTree(s1);
			JsonNode rootNode2 = mapper.readTree(s2);

			isEqual = rootNode.equals(rootNode2);

		} catch (IOException e) {
			throw e;
		}

		return isEqual;

	}

	public static final HashMap<String, String> parseLinks(String links) {

		// parse header link
		// Link: <https://api.invoiced.com/customers?page=3&per_page=10>;
		// rel="self",
		// <https://api.invoiced.com/customers?page=1&per_page=10>; rel="first",
		// <https://api.invoiced.com/customers?page=2&per_page=10>;
		// rel="previous",
		// <https://api.invoiced.com/customers?page=4&per_page=10>; rel="next",
		// <https://api.invoiced.com/customers?page=5&per_page=10>; rel="last"

		HashMap<String, String> linkMap = new HashMap<String, String>();

		String[] parsedLinks = links.split(",");

		for (String parsedLink : parsedLinks) {

			String[] reParse = parsedLink.split(";");

			String link = reParse[0].trim().replaceAll("<", "").replaceAll(">", "");
			String next = reParse[1].trim();

			int begin = next.indexOf("\"");
			int end = next.lastIndexOf("\"");

			next = next.substring(begin + 1, end);

			linkMap.put(next, link);

		}

		return linkMap;
	}

	// Method take from
	// http://stackoverflow.com/questions/5902090/how-to-extract-parameters-from-a-given-url
	// public final static Map<String, List<String>> getQueryParams(String url)
	// {
	// try {
	// Map<String, List<String>> params = new HashMap<String, List<String>>();
	// String[] urlParts = url.split("\\?");
	// if (urlParts.length > 1) {
	// String query = urlParts[1];
	// for (String param : query.split("&")) {
	// String[] pair = param.split("=");
	// String key = URLDecoder.decode(pair[0], "UTF-8");
	// String value = "";
	// if (pair.length > 1) {
	// value = URLDecoder.decode(pair[1], "UTF-8");
	// }

	// List<String> values = params.get(key);
	// if (values == null) {
	// values = new ArrayList<String>();
	// params.put(key, values);
	// }
	// values.add(value);
	// }
	// }

	// return params;
	// } catch (UnsupportedEncodingException ex) {
	// throw new AssertionError(ex);
	// }
	// }

}