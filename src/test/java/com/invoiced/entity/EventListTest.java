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

public class EventListTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testForCoverage() {

		Connection conn = new Connection("", true);
		conn.testModeOn();
		Event event = conn.newEvent();
		event.id = 231;
		assertTrue("Event Entity id is wrong", event.getEntityId() == 231);
		assertTrue("Event id is wrong", event.getParentID() == -1);
		try {
			event.create();
			event.retrieve(23423);
			event.delete();
			event.setParentID(-1231);
			assertTrue("Event id is wrong", event.getParentID() == -1);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testList() {

		// references connection_rr_11.json
		// references connection_rr_51.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Event event = conn.newEvent();
			EntityList<Event> events = event.listAll();

			assertTrue("Total count is incorrect", events.getTotalCount() == 2);
			assertTrue("Id1 is incorrect", events.get(0).id == 1228003L);
			assertTrue("Id2 is incorrect", events.get(1).id == 1228004L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testJsonSerialization() {

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonInString = "[{\n    \"id\": 1228003,\n    \"type\": \"transaction.created\",\n    \"timestamp\": 1451500772,\n    \"data\": {\n        \"object\": {\n            \"amount\": 55,\n            \"created_at\": 1451500772,\n            \"currency\": \"usd\",\n            \"customer\": 15455,\n            \"date\": 1451500771,\n            \"gateway\": null,\n            \"gateway_id\": null,\n            \"payment_source\": null,\n            \"id\": 212047,\n            \"invoice\": 196539,\n            \"method\": \"other\",\n            \"notes\": null,\n            \"parent_transaction\": null,\n            \"status\": \"succeeded\",\n            \"theme\": null,\n            \"type\": \"payment\",\n            \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/59FHO96idoXFeiBDu1y5Zggg/pdf\",\n            \"metadata\": {}\n        }}\n    },{\n    \"id\": 1228004,\n    \"type\": \"transaction.created\",\n    \"timestamp\": 1451500772,\n    \"data\": {\n        \"object\": {\n            \"amount\": 55,\n            \"created_at\": 1451500772,\n            \"currency\": \"usd\",\n            \"customer\": 15455,\n            \"date\": 1451500771,\n            \"gateway\": null,\n            \"gateway_id\": null,\n            \"payment_source\": null,\n            \"id\": 212047,\n            \"invoice\": 196539,\n            \"method\": \"other\",\n            \"notes\": null,\n            \"parent_transaction\": null,\n            \"status\": \"succeeded\",\n            \"theme\": null,\n            \"type\": \"payment\",\n            \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/59FHO96idoXFeiBDu1y5Zggg/pdf\",\n            \"metadata\": {}\n        }}\n    }]";

			EntityList<Event> c1 = mapper.readValue(jsonInString, new TypeReference<EntityList<Event>>() {
			});

			assertTrue("Size is incorrect", c1.size() == 2);
			assertTrue("Id1 is incorrect", c1.get(0).id == 1228003L);
			assertTrue("Id2 is incorrect", c1.get(1).id == 1228004L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}