package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InvoiceListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testList() {

        // references connection_rr_46.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        try {
            EntityList<Invoice> invoices = conn.newInvoice().listAll();

            assertTrue("Invoice 1 id is incorrect", invoices.get(0).id == 46225L);

            assertTrue("Invoice 1 id is incorrect", invoices.get(1).id == 46226L);

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
                    "[{\n  \"id\": 46225,\n  \"customer\": 15444,\n  \"name\": null,\n  \"currency\": \"usd\",\n  \"draft\": false,\n  \"closed\": false,\n  \"paid\": false,\n  \"status\": \"not_sent\",\n  \"chase\": false,\n  \"next_chase_on\": null,\n  \"autopay\": false,\n  \"attempt_count\": 0,\n  \"next_payment_attempt\": null,\n  \"subscription\": null,\n  \"number\": \"INV-0016\",\n  \"date\": 1416290400,\n  \"due_date\": 1417500000,\n  \"payment_terms\": \"NET 14\",\n  \"items\": [\n    {\n      \"id\": 7,\n      \"catalog_item\": null,\n      \"type\": \"product\",\n      \"name\": \"Copy Paper, Case\",\n      \"description\": null,\n      \"quantity\": 1,\n      \"unit_cost\": 45,\n      \"amount\": 45,\n      \"discountable\": true,\n      \"discounts\": [],\n      \"taxable\": true,\n      \"taxes\": [],\n      \"metadata\": {}\n    },\n    {\n      \"id\": 8,\n      \"catalog_item\": \"delivery\",\n      \"type\": \"service\",\n      \"name\": \"Delivery\",\n      \"description\": \"\",\n      \"quantity\": 1,\n      \"unit_cost\": 10,\n      \"amount\": 10,\n      \"discountable\": true,\n      \"discounts\": [],\n      \"taxable\": true,\n      \"taxes\": [],\n      \"metadata\": {}\n    }\n  ],\n  \"notes\": null,\n  \"subtotal\": 55,\n  \"discounts\": [],\n  \"taxes\": [\n    {\n      \"id\": 20554,\n      \"amount\": 3.85,\n      \"tax_rate\": null\n    }\n  ],\n  \"total\": 51.15,\n  \"balance\": 51.15,\n  \"tags\": [],\n  \"url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY\",\n  \"payment_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/payment\",\n  \"pdf_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n  \"created_at\": 1415229884,\n  \"metadata\": {}\n},{\n  \"id\": 46226,\n  \"customer\": 15444,\n  \"name\": null,\n  \"currency\": \"usd\",\n  \"draft\": false,\n  \"closed\": false,\n  \"paid\": false,\n  \"status\": \"not_sent\",\n  \"chase\": false,\n  \"next_chase_on\": null,\n  \"autopay\": false,\n  \"attempt_count\": 0,\n  \"next_payment_attempt\": null,\n  \"subscription\": null,\n  \"number\": \"INV-0016\",\n  \"date\": 1416290400,\n  \"due_date\": 1417500000,\n  \"payment_terms\": \"NET 14\",\n  \"items\": [\n    {\n      \"id\": 7,\n      \"catalog_item\": null,\n      \"type\": \"product\",\n      \"name\": \"Copy Paper, Case\",\n      \"description\": null,\n      \"quantity\": 1,\n      \"unit_cost\": 45,\n      \"amount\": 45,\n      \"discountable\": true,\n      \"discounts\": [],\n      \"taxable\": true,\n      \"taxes\": [],\n      \"metadata\": {}\n    },\n    {\n      \"id\": 8,\n      \"catalog_item\": \"delivery\",\n      \"type\": \"service\",\n      \"name\": \"Delivery\",\n      \"description\": \"\",\n      \"quantity\": 1,\n      \"unit_cost\": 10,\n      \"amount\": 10,\n      \"discountable\": true,\n      \"discounts\": [],\n      \"taxable\": true,\n      \"taxes\": [],\n      \"metadata\": {}\n    }\n  ],\n  \"notes\": null,\n  \"subtotal\": 55,\n  \"discounts\": [],\n  \"taxes\": [\n    {\n      \"id\": 20554,\n      \"amount\": 3.85,\n      \"tax_rate\": null\n    }\n  ],\n  \"total\": 51.15,\n  \"balance\": 51.15,\n  \"tags\": [],\n  \"url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY\",\n  \"payment_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/payment\",\n  \"pdf_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n  \"created_at\": 1415229884,\n  \"metadata\": {}\n}]";

            EntityList<Invoice> i1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<Invoice>>() {
                    });

            assertTrue("Id1 is incorrect", i1.get(0).id == 46225L);
            assertTrue("Id2 is incorrect", i1.get(1).id == 46226L);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testJsonDeserialization() {
    }
}
