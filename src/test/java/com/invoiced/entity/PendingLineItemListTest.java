package com.invoiced.entity;

import org.junit.Test;
import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import java.sql.Timestamp;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;

import com.fasterxml.jackson.core.type.TypeReference;

public class PendingLineItemListTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testList() {

		// references connection_rr_11.json
		// references connection_rr_50.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Customer cust = conn.newCustomer().retrieve(11);
			PendingLineItem pli = cust.newPendingLineItem();
			EntityList<PendingLineItem> plis = pli.listAll();

			assertTrue("PendingLineItem 1 id is incorrect", plis.get(0).id == 8);

			assertTrue("PendingLineItem 1 id is incorrect", plis.get(1).id == 9);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testJsonSerialization() {

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonInString = "[{\n  \"id\": 8,\n  \"catalog_item\": \"delivery\",\n  \"type\": \"service\",\n  \"name\": \"Delivery\",\n  \"description\": \"\",\n  \"quantity\": 1,\n  \"unit_cost\": 10,\n  \"amount\": 10,\n  \"discountable\": true,\n  \"discounts\": [],\n  \"taxable\": true,\n  \"taxes\": [],\n  \"metadata\": {}\n},{\n  \"id\": 9,\n  \"catalog_item\": \"delivery\",\n  \"type\": \"service\",\n  \"name\": \"Delivery\",\n  \"description\": \"\",\n  \"quantity\": 1,\n  \"unit_cost\": 10,\n  \"amount\": 10,\n  \"discountable\": true,\n  \"discounts\": [],\n  \"taxable\": true,\n  \"taxes\": [],\n  \"metadata\": {}\n}]";

			EntityList<PendingLineItem> c1 = mapper.readValue(jsonInString,
					new TypeReference<EntityList<PendingLineItem>>() {
					});

			assertTrue("Size is incorrect", c1.size() == 2);
			assertTrue("Id1 is incorrect", c1.get(0).id == 8);
			assertTrue("Id2 is incorrect", c1.get(1).id == 9);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}