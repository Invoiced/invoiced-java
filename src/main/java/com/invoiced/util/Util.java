package com.invoiced.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.util.HashMap;

public class Util {

    private static final ObjectMapper mapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setFilterProvider(
                            new SimpleFilterProvider().addFilter("customFilter", null).setFailOnUnknownId(false));

    // generates mapper which filters out properties from `exclusions` list
    public static final ObjectMapper getFilteredMapper(String[] exclusions) {
        ObjectMapper filteredMapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter(
                "customFilter", SimpleBeanPropertyFilter.serializeAllExcept(exclusions));

        filteredMapper.setFilterProvider(filterProvider.setFailOnUnknownId(false));
        return filteredMapper;
    }

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
        // Example:
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
}
