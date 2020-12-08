package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ItemTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references connection_rr_95.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Item item = conn.newItem();
        item.id = "delivery";
        item.object = "failure_condition";
        item.name = "Delivery";
        item.type = "service";
        item.unitCost = 100L;

        try {
            item.create();

            assertTrue("Item id is incorrect", item.id.equals("delivery"));
            assertTrue("createdAt is not populated", item.createdAt == 1477327516);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references connection_rr_96.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Item item = conn.newItem().retrieve("delivery");

            assertTrue("Item type is incorrect", item.type.equals("service"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references connection_rr_97.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {

            Item item = conn.newItem().retrieve("delivery");
            item.name = "Updated";

            item.save();

            assertTrue("Item name is incorrect", item.name.equals("Updated"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {

        // references connection_rr_98.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Item item = conn.newItem().retrieve("delivery");
            item.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testList() {

        // references connection_rr_99.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<Item> items = conn.newItem().listAll();

            assertTrue("Id 1 is incorrect", items.get(0).id.equals("delivery"));

            assertTrue("Id 2 is incorrect", items.get(1).id.equals("delivery2"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
