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

public class PlanTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_100.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

        Plan plan = conn.newPlan();
        plan.id = "starter";
        plan.name = "Starter";
        plan.catalogItem = "software-subscription";
        plan.amount = 49L;
        plan.interval = "month";
        plan.intervalCount = 1L;
        plan.pricingMode = "per_unit";

		try {
			plan.create();

			assertTrue("Plan id is incorrect", plan.id.equals("starter"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_101.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Plan plan = conn.newPlan().retrieve("starter");

			assertTrue("Plan pricing mode is incorrect", plan.pricingMode.equals("per_unit"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_101.json
		// references connection_rr_102.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Plan plan = conn.newPlan().retrieve("starter");
			plan.name = "Updated";

			plan.save();

			assertTrue("Plan name is incorrect", plan.name.equals("Updated"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_101.json
		// references connection_rr_103.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Plan plan = conn.newPlan().retrieve("starter");
			plan.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

    }
    
	@Test
	public void testList() {

		// references connection_rr_104.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			EntityList<Plan> plans = conn.newPlan().listAll();

			assertTrue("Id 1 is incorrect", plans.get(0).id.equals("starter"));

			assertTrue("Id 2 is incorrect", plans.get(1).id .equals("starter2"));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}