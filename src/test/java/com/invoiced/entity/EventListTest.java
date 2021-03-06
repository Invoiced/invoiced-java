package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EventListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test(expected = Exception.class)
    public void testForCoverage() throws Exception {

        Connection conn = new Connection("api_key", "http://localhost:8080");

        Event event = conn.newEvent();
        event.id = 231L;
        assertTrue("Event Entity id is wrong", event.getEntityId().equals("231"));
        try {
            event.create();
            event.delete();

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Test
    public void testForRetrieve() {

        Connection conn = new Connection("api_key", "http://localhost:8080");

        Event event = conn.newEvent();
        try {
            event = event.retrieve(1228003);
            assertTrue("Event type is wrong", event.type.equals("invoice.paid"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testList() {

        // references customers_retrieve_2.json
        // references events_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {

            Event event = conn.newEvent();
            EntityList<Event> events = event.listAll();

            assertTrue("Total count is incorrect", events.getTotalCount() == 2);
            assertTrue("Id1 is incorrect", events.get(0).id == 11296106L);
            assertTrue("Id2 is incorrect", events.get(1).id == 11296107L);

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
                    "[{\n    \"data\": {\n      \"object\": {\n        \"attempt_count\": 0,\n        \"autopay\": false,\n        \"balance\": 51.15,\n        \"closed\": false,\n        \"created_at\": 1415229884,\n        \"currency\": \"usd\",\n        \"customer\": {\n          \"address1\": null,\n          \"address2\": null,\n          \"attention_to\": null,\n          \"autopay\": true,\n          \"autopay_delay_days\": -1,\n          \"avalara_entity_use_code\": null,\n          \"avalara_exemption_number\": null,\n          \"bill_to_parent\": false,\n          \"chase\": true,\n          \"chasing_cadence\": 82,\n          \"city\": null,\n          \"consolidated\": false,\n          \"country\": \"US\",\n          \"created_at\": 1589566232,\n          \"credit_hold\": false,\n          \"credit_limit\": null,\n          \"currency\": \"usd\",\n          \"email\": \"abc@abc.com\",\n          \"id\": 876577,\n          \"language\": null,\n          \"metadata\": {},\n          \"name\": \"abc\",\n          \"next_chase_step\": 312,\n          \"notes\": null,\n          \"number\": \"CUST-0087\",\n          \"object\": \"customer\",\n          \"owner\": null,\n          \"parent_customer\": null,\n          \"payment_source\": {\n            \"brand\": \"Visa\",\n            \"chargeable\": true,\n            \"created_at\": 1589566234,\n            \"exp_month\": 2,\n            \"exp_year\": 2023,\n            \"failure_reason\": null,\n            \"funding\": \"credit\",\n            \"gateway\": \"stripe\",\n            \"gateway_customer\": \"cus_HHhOkejFJ9cz3D\",\n            \"gateway_id\": \"card_1Gj7zQKzrtJqUQfwNpbLD70j\",\n            \"id\": 13320,\n            \"last4\": \"4242\",\n            \"merchant_account\": 230,\n            \"object\": \"card\",\n            \"receipt_email\": null\n          },\n          \"payment_terms\": null,\n          \"phone\": null,\n          \"postal_code\": null,\n          \"sign_up_page\": 2240,\n          \"sign_up_url\": \"https://dundermifflin.invoiced.com/sign_up/divIfSVP66jR7YSb10D6c67k\",\n          \"state\": null,\n          \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/divIfSVP66jR7YSb10D6c67k/pdf\",\n          \"tax_id\": null,\n          \"taxable\": true,\n          \"taxes\": [],\n          \"type\": \"company\"\n        },\n        \"date\": 1416290400,\n        \"discounts\": [],\n        \"draft\": false,\n        \"due_date\": 1417500000,\n        \"id\": 46225,\n        \"items\": [\n          {\n            \"amount\": 45,\n            \"catalog_item\": null,\n            \"description\": null,\n            \"discountable\": true,\n            \"discounts\": [],\n            \"id\": 7,\n            \"metadata\": {},\n            \"name\": \"Copy Paper, Case\",\n            \"object\": \"line_item\",\n            \"quantity\": 1,\n            \"taxable\": true,\n            \"taxes\": [],\n            \"type\": \"product\",\n            \"unit_cost\": 45\n          },\n          {\n            \"amount\": 10,\n            \"catalog_item\": \"delivery\",\n            \"description\": null,\n            \"discountable\": true,\n            \"discounts\": [],\n            \"id\": 8,\n            \"metadata\": {},\n            \"name\": \"Delivery\",\n            \"object\": \"line_item\",\n            \"quantity\": 1,\n            \"taxable\": true,\n            \"taxes\": [],\n            \"type\": \"service\",\n            \"unit_cost\": 10\n          }\n        ],\n        \"metadata\": {},\n        \"name\": null,\n        \"next_payment_attempt\": null,\n        \"notes\": null,\n        \"number\": \"INV-0016\",\n        \"object\": \"invoice\",\n        \"paid\": false,\n        \"payment_terms\": \"NET 14\",\n        \"payment_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/payment\",\n        \"pdf_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n        \"status\": \"not_sent\",\n        \"subscription\": null,\n        \"subtotal\": 55,\n        \"taxes\": [\n          {\n            \"amount\": 3.85,\n            \"id\": 20554,\n            \"object\": \"tax\",\n            \"tax_rate\": null\n          }\n        ],\n        \"total\": 51.15,\n        \"url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY\"\n      }\n    },\n    \"id\": 11296106,\n    \"previous\": {\n      \"attempt_count\": 0,\n      \"balance\": 39,\n      \"closed\": false,\n      \"next_payment_attempt\": 1606806029,\n      \"paid\": false,\n      \"status\": \"not_sent\"\n    },\n    \"timestamp\": 1606806943,\n    \"type\": \"invoice.paid\",\n    \"user\": {\n      \"created_at\": null,\n      \"email\": null,\n      \"first_name\": null,\n      \"id\": -2,\n      \"last_name\": null,\n      \"registered\": true,\n      \"two_factor_enabled\": false\n    }\n  },\n{\n    \"data\": {\n      \"object\": {\n        \"attempt_count\": 0,\n        \"autopay\": false,\n        \"balance\": 51.15,\n        \"closed\": false,\n        \"created_at\": 1415229884,\n        \"currency\": \"usd\",\n        \"customer\": {\n          \"address1\": null,\n          \"address2\": null,\n          \"attention_to\": null,\n          \"autopay\": true,\n          \"autopay_delay_days\": -1,\n          \"avalara_entity_use_code\": null,\n          \"avalara_exemption_number\": null,\n          \"bill_to_parent\": false,\n          \"chase\": true,\n          \"chasing_cadence\": 82,\n          \"city\": null,\n          \"consolidated\": false,\n          \"country\": \"US\",\n          \"created_at\": 1589566232,\n          \"credit_hold\": false,\n          \"credit_limit\": null,\n          \"currency\": \"usd\",\n          \"email\": \"abc@abc.com\",\n          \"id\": 876577,\n          \"language\": null,\n          \"metadata\": {},\n          \"name\": \"abc\",\n          \"next_chase_step\": 312,\n          \"notes\": null,\n          \"number\": \"CUST-0087\",\n          \"object\": \"customer\",\n          \"owner\": null,\n          \"parent_customer\": null,\n          \"payment_source\": {\n            \"brand\": \"Visa\",\n            \"chargeable\": true,\n            \"created_at\": 1589566234,\n            \"exp_month\": 2,\n            \"exp_year\": 2023,\n            \"failure_reason\": null,\n            \"funding\": \"credit\",\n            \"gateway\": \"stripe\",\n            \"gateway_customer\": \"cus_HHhOkejFJ9cz3D\",\n            \"gateway_id\": \"card_1Gj7zQKzrtJqUQfwNpbLD70j\",\n            \"id\": 13320,\n            \"last4\": \"4242\",\n            \"merchant_account\": 230,\n            \"object\": \"card\",\n            \"receipt_email\": null\n          },\n          \"payment_terms\": null,\n          \"phone\": null,\n          \"postal_code\": null,\n          \"sign_up_page\": 2240,\n          \"sign_up_url\": \"https://dundermifflin.invoiced.com/sign_up/divIfSVP66jR7YSb10D6c67k\",\n          \"state\": null,\n          \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/divIfSVP66jR7YSb10D6c67k/pdf\",\n          \"tax_id\": null,\n          \"taxable\": true,\n          \"taxes\": [],\n          \"type\": \"company\"\n        },\n        \"date\": 1416290400,\n        \"discounts\": [],\n        \"draft\": false,\n        \"due_date\": 1417500000,\n        \"id\": 46225,\n        \"items\": [\n          {\n            \"amount\": 45,\n            \"catalog_item\": null,\n            \"description\": null,\n            \"discountable\": true,\n            \"discounts\": [],\n            \"id\": 7,\n            \"metadata\": {},\n            \"name\": \"Copy Paper, Case\",\n            \"object\": \"line_item\",\n            \"quantity\": 1,\n            \"taxable\": true,\n            \"taxes\": [],\n            \"type\": \"product\",\n            \"unit_cost\": 45\n          },\n          {\n            \"amount\": 10,\n            \"catalog_item\": \"delivery\",\n            \"description\": null,\n            \"discountable\": true,\n            \"discounts\": [],\n            \"id\": 8,\n            \"metadata\": {},\n            \"name\": \"Delivery\",\n            \"object\": \"line_item\",\n            \"quantity\": 1,\n            \"taxable\": true,\n            \"taxes\": [],\n            \"type\": \"service\",\n            \"unit_cost\": 10\n          }\n        ],\n        \"metadata\": {},\n        \"name\": null,\n        \"next_payment_attempt\": null,\n        \"notes\": null,\n        \"number\": \"INV-0016\",\n        \"object\": \"invoice\",\n        \"paid\": false,\n        \"payment_terms\": \"NET 14\",\n        \"payment_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/payment\",\n        \"pdf_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n        \"status\": \"not_sent\",\n        \"subscription\": null,\n        \"subtotal\": 55,\n        \"taxes\": [\n          {\n            \"amount\": 3.85,\n            \"id\": 20554,\n            \"object\": \"tax\",\n            \"tax_rate\": null\n          }\n        ],\n        \"total\": 51.15,\n        \"url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY\"\n      }\n    },\n    \"id\": 11296107,\n    \"previous\": {\n      \"attempt_count\": 0,\n      \"balance\": 39,\n      \"closed\": false,\n      \"next_payment_attempt\": 1606806029,\n      \"paid\": false,\n      \"status\": \"not_sent\"\n    },\n    \"timestamp\": 1606806943,\n    \"type\": \"invoice.paid\",\n    \"user\": {\n      \"created_at\": null,\n      \"email\": null,\n      \"first_name\": null,\n      \"id\": -2,\n      \"last_name\": null,\n      \"registered\": true,\n      \"two_factor_enabled\": false\n    }\n  }\n]";

            EntityList<Event> c1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<Event>>() {
                    });

            assertTrue("Size is incorrect", c1.size() == 2);
            assertTrue("Id1 is incorrect", c1.get(0).id == 11296106L);
            assertTrue("Id2 is incorrect", c1.get(1).id == 11296107L);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
