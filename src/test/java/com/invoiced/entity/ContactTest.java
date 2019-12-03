package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class ContactTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_11.json
		// references connection_rr_33.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			Contact contact = cust.newContact();
			contact.name = "Nancy Talty";
			contact.email = "nancy.talty@example.com";
			contact.create();

			String s = contact.toString();
			assertTrue("Contact string is incorrect", s.contains("10403"));

			assertTrue("Contact id is incorrect", contact.id == 10403);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_11.json
		// references connection_rr_34.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			Contact contact = cust.newContact().retrieve(10403);

			assertTrue("Contact e-mail is incorrect", contact.email.equals("nancy.talty@example.com"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_11.json
		// references connection_rr_35.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Customer cust = conn.newCustomer().retrieve(11);
			Contact contact = cust.newContact().retrieve(10403);
			contact.address1 = "507 Grove Avenue";
			contact.city = "Oklahoma City";
			contact.state = "OK";
			contact.postalCode = "73102";

			contact.save();

			assertTrue("Customer City is incorrect", contact.city.equals("Oklahoma City"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_36.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			Contact contact = cust.newContact().retrieve(10403);
			contact.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testJsonSerialization() {
		new Contact(null);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonInString = "{\n  \"id\": 10403,\n  \"name\": \"Nancy Talty\",\n  \"email\": \"nancy.talty@example.com\",\n  \"primary\": true,\n  \"address1\": \"507 Grove Avenue\",\n  \"address2\": null,\n  \"city\": \"Oklahoma City\",\n  \"state\": \"OK\",\n  \"postal_code\": \"73102\",\n  \"country\": null,\n  \"created_at\": 1463510889\n}";

			Contact c1 = mapper.readValue(jsonInString, Contact.class);

			assertTrue("Id is incorrect", c1.id == 10403L);
			assertTrue("Name is incorrect", c1.name.equals("Nancy Talty"));
			assertTrue("Email is incorrect", c1.email.equals("nancy.talty@example.com"));
			assertTrue("Primary is incorrect", c1.primary == true);
			assertTrue("Address1 is incorrect", c1.address1.equals("507 Grove Avenue"));
			assertTrue("Address2 is incorrect", c1.address2 == null);
			assertTrue("City is incorrect", c1.city.equals("Oklahoma City"));
			assertTrue("State is incorrect", c1.state.equals("OK"));
			assertTrue("PostalCode is incorrect", c1.postalCode.equals("73102"));
			assertTrue("Country is incorrect", c1.country == null);
			assertTrue("CreatedAt is incorrect", c1.createdAt == 1463510889L);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
			fail();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}