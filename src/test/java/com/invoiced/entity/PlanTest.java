package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PlanTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references plans_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Plan plan = conn.newPlan();
        plan.id = "starter";
        plan.name = "Starter";
        plan.item = "software-subscription";
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

        // references plans_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Plan plan = conn.newPlan().retrieve("starter");

            assertTrue("Plan pricing mode is incorrect", plan.pricingMode.equals("per_unit"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references plans_retrieve.json
        // references plans_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        // references plans_retrieve.json
        // references plans_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Plan plan = conn.newPlan().retrieve("starter");
            plan.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testList() {

        // references plans_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<Plan> plans = conn.newPlan().listAll();

            assertTrue("Id 1 is incorrect", plans.get(0).id.equals("starter"));

            assertTrue("Id 2 is incorrect", plans.get(1).id.equals("starter2"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
