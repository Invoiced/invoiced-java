package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CustomerListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testList() {

        // references connection_rr_45.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<Customer> customers = conn.newCustomer().listAll(null);

            assertTrue("Customer 1 id is incorrect", customers.get(0).id == 15444);

            assertTrue("Customer 1 id is incorrect", customers.get(1).id == 15445);

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
                    "[{\n  \"id\": 15444,\n  \"name\": \"Acme\",\n  \"number\": \"CUST-0001\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": true,\n  \"payment_terms\": null,\n  \"payment_source\": {\n    \"id\": 850,\n    \"object\": \"card\",\n    \"brand\": \"Visa\",\n    \"last4\": \"4242\",\n    \"exp_month\": 2,\n    \"exp_year\": 20,\n    \"funding\": \"credit\"\n  },\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": null,\n  \"address1\": null,\n  \"address2\": null,\n  \"city\": null,\n  \"state\": null,\n  \"postal_code\": null,\n  \"country\": \"US\",\n  \"tax_id\": null,\n  \"phone\": null,\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n},{\n  \"id\": 15445,\n  \"name\": \"Acme\",\n  \"number\": \"CUST-0001\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": true,\n  \"payment_terms\": null,\n  \"payment_source\": {\n    \"id\": 850,\n    \"object\": \"card\",\n    \"brand\": \"Visa\",\n    \"last4\": \"4242\",\n    \"exp_month\": 2,\n    \"exp_year\": 20,\n    \"funding\": \"credit\"\n  },\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": null,\n  \"address1\": null,\n  \"address2\": null,\n  \"city\": null,\n  \"state\": null,\n  \"postal_code\": null,\n  \"country\": \"US\",\n  \"tax_id\": null,\n  \"phone\": null,\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}]";

            EntityList<Customer> c1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<Customer>>() {
                    });

            assertTrue("Size is incorrect", c1.size() == 2);
            assertTrue("Id1 is incorrect", c1.get(0).id == 15444L);
            assertTrue("Id2 is incorrect", c1.get(1).id == 15445L);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
