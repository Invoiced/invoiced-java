package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ContactListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testList() {

        // references customers_retrieve_2.json
        // references customers_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {

            Customer cust = conn.newCustomer().retrieve(11);
            Contact contact = cust.newContact();
            EntityList<Contact> contacts = contact.listAll();

            assertTrue("Contact 1 id is incorrect", contacts.get(0).id == 10403L);

            assertTrue("Contact 1 id is incorrect", contacts.get(1).id == 10404L);

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
                    "[ {\n  \"id\": 10403,\n  \"name\": \"Nancy Talty\",\n  \"email\": \"nancy.talty@example.com\",\n  \"primary\": true,\n  \"address1\": \"507 Grove Avenue\",\n  \"address2\": null,\n  \"city\": \"Oklahoma City\",\n  \"state\": \"OK\",\n  \"postal_code\": \"73102\",\n  \"country\": null,\n  \"created_at\": 1463510889\n},{\n  \"id\": 10404,\n  \"name\": \"Nancy Talty\",\n  \"email\": \"nancy.talty@example.com\",\n  \"primary\": true,\n  \"address1\": \"507 Grove Avenue\",\n  \"address2\": null,\n  \"city\": \"Oklahoma City\",\n  \"state\": \"OK\",\n  \"postal_code\": \"73102\",\n  \"country\": null,\n  \"created_at\": 1463510889\n}]";

            EntityList<Contact> c1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<Contact>>() {
                    });

            assertTrue("Size is incorrect", c1.size() == 2);
            assertTrue("Id1 is incorrect", c1.get(0).id == 10403L);
            assertTrue("Id2 is incorrect", c1.get(1).id == 10404L);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
