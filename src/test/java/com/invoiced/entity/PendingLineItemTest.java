package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class PendingLineItemTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_11.json
		// references connection_rr_37.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			PendingLineItem pli = cust.newPendingLineItem();
			pli.catalogItem = "delivery";
			pli.create();

			assertTrue("Pending Line Item id is incorrect", pli.id == 8);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_11.json
		// references connection_rr_38.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			PendingLineItem pli = cust.newPendingLineItem().retrieve(8);

			assertTrue("Catalog Item is incorrect", pli.catalogItem.equals("delivery"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_11.json
		// references connection_rr_39.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Customer cust = conn.newCustomer().retrieve(11);
			PendingLineItem pli = cust.newPendingLineItem().retrieve(8);

			pli.quantity = 2;

			pli.save();

			// assertTrue("Customer City is incorrect",
			// contact.city.equals("Oklahoma City"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_40.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			PendingLineItem pli = cust.newPendingLineItem().retrieve(8);

			pli.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testJsonSerialization() {

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonInString = "{\n  \"id\": 8,\n  \"catalog_item\": \"delivery\",\n  \"type\": \"service\",\n  \"name\": \"Delivery\",\n  \"description\": \"\",\n  \"quantity\": 1,\n  \"unit_cost\": 10,\n  \"amount\": 10,\n  \"discountable\": true,\n  \"discounts\": [],\n  \"taxable\": true,\n  \"taxes\": [],\n  \"metadata\": {}\n}";

			PendingLineItem c1 = mapper.readValue(jsonInString, PendingLineItem.class);

			assertTrue("Id is incorrect", c1.id == 8);
			assertTrue("Catalog Item is incorrect", c1.catalogItem.equals("delivery"));
			assertTrue("Type is incorrect", c1.type.equals("service"));

			assertTrue("Description is incorrect", c1.description == "");
			assertTrue("Quantity is incorrect", c1.quantity == 1);
			assertTrue("Unit Cost is incorrect", c1.unitCost == 10);
			assertTrue("Amount is incorrect", c1.amount == 10);
			assertTrue("Discountable is incorrect", c1.discountable == true);
			assertTrue("Discounts is incorrect", c1.discounts.length == 0);
			assertTrue("Taxable is incorrect", c1.taxable == true);
			assertTrue("Taxes is incorrect", c1.taxes.length == 0);

			assertTrue("Metadata is incorrect", c1.metadata != null);

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