package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PendingLineItemListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testList() {

        // references customers_retrieve_2.json
        // references pending_line_items_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonInString =
                    "[{\n  \"id\": 8,\n  \"catalog_item\": \"delivery\",\n  \"type\": \"service\",\n  \"name\": \"Delivery\",\n  \"description\": \"\",\n  \"quantity\": 1,\n  \"unit_cost\": 10,\n  \"amount\": 10,\n  \"discountable\": true,\n  \"discounts\": [],\n  \"taxable\": true,\n  \"taxes\": [],\n  \"metadata\": {}\n},{\n  \"id\": 9,\n  \"catalog_item\": \"delivery\",\n  \"type\": \"service\",\n  \"name\": \"Delivery\",\n  \"description\": \"\",\n  \"quantity\": 1,\n  \"unit_cost\": 10,\n  \"amount\": 10,\n  \"discountable\": true,\n  \"discounts\": [],\n  \"taxable\": true,\n  \"taxes\": [],\n  \"metadata\": {}\n}]";

            EntityList<PendingLineItem> c1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<PendingLineItem>>() {
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
