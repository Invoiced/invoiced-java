
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

public class EstimateTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testParentID() {
		Connection conn = new Connection("", true);
		conn.testModeOn();

		Estimate estimate = conn.newEstimate();
		assertTrue("Estimate Parent Id is incorrect", estimate.getParentID() == -1);
		estimate.setParentID(-4);
		assertTrue("Estimate Parent Id is incorrect", estimate.getParentID() == -1);

	}

	@Test
	public void testCreate() {

		// references connection_rr_63.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Estimate estimate = conn.newEstimate();

		estimate.customer = 15444L;
		estimate.paymentTerms = "NET 30";
		LineItem[] items = new LineItem[2];
		items[0] = new LineItem();
		items[0].name = "Copy paper, Case";
		items[0].quantity = 1D;
		items[0].unitCost = 50D;
		items[1] = new LineItem();
		items[1].catalogItem = "delivery";
		items[1].quantity = 1D;
		Tax[] taxes = new Tax[1];
		taxes[0] = new Tax();
		taxes[0].amount = 3.85D;
		estimate.items = items;
		estimate.taxes = taxes;

		try {

			estimate.create();

			assertTrue("Estimate Id is incorrect", estimate.id == 46999L);

			assertTrue("Estimate Item Id is incorrect", estimate.items[0].id == 7);

			assertTrue("Estimate Item Id is incorrect", estimate.items[1].id == 8);

			assertTrue("Tax Id is incorrect", estimate.taxes[0].id == 20554);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_64.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Estimate estimate = conn.newEstimate();

		try {
			estimate = estimate.retrieve(46999);
			assertTrue("Estimate currency is incorrect",
			           estimate.currency.equals("usd"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_64.json
		// references connection_rr_65.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Estimate estimate = conn.newEstimate();

		try {
			estimate = estimate.retrieve(46999);
			estimate.name = "July Paper Delivery";
			estimate.status = "sent";

			estimate.save();

			assertTrue("Estimate status should be sent", estimate.status.equals("sent"));

			assertTrue("Estimate name should be updated", estimate.name.equals("July Paper Delivery"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	// }

	@Test
	public void testDelete() {

		// references connection_rr_64.json
		// references connection_rr_66.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Estimate estimate = conn.newEstimate();

		try {
			estimate = estimate.retrieve(46999);
			estimate.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testList() {

		// references connection_rr_67.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			EntityList<Estimate> estimates = conn.newEstimate().listAll();

			assertTrue("Estimate 1 id is incorrect", estimates.get(0).id == 46999L);

			assertTrue("Estimate 1 id is incorrect", estimates.get(1).id == 46700L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}