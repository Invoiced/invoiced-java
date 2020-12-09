package com.invoiced.entity;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreditBalanceAdjustmentTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references credit_balance_adjustments_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        CreditBalanceAdjustment creditBalanceAdjustment = conn.newCreditBalanceAdjustment();
        creditBalanceAdjustment.customer = 1234L;
        creditBalanceAdjustment.currency = "usd";
        creditBalanceAdjustment.amount = 800D;
        creditBalanceAdjustment.notes = "1450";

        try {
            creditBalanceAdjustment.create();
            assertTrue("CreditBalanceAdjustment id is incorrect", creditBalanceAdjustment.id == 20939);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references credit_balance_adjustments_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditBalanceAdjustment creditBalanceAdjustment = conn.newCreditBalanceAdjustment();

        try {
            creditBalanceAdjustment = creditBalanceAdjustment.retrieve(20939);

            assertTrue("Credit balance adjustment customer is incorrect", creditBalanceAdjustment.customer == 15460);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references credit_balance_adjustments_retrieve.json
        // references credit_balance_adjustments_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        CreditBalanceAdjustment creditBalanceAdjustment = conn.newCreditBalanceAdjustment();

        try {
            creditBalanceAdjustment = creditBalanceAdjustment.retrieve(20939);
            creditBalanceAdjustment.notes = "Credit for service outage";
            creditBalanceAdjustment.save();

            assertTrue(
                    "Credit balance adjustment should have been updated",
                    creditBalanceAdjustment.notes.equals("Credit for service outage"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {

        // references credit_balance_adjustments_retrieve.json
        // references credit_balance_adjustments_void.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        CreditBalanceAdjustment creditBalanceAdjustment = conn.newCreditBalanceAdjustment();

        try {
            creditBalanceAdjustment = creditBalanceAdjustment.retrieve(20939);
            creditBalanceAdjustment.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {
        new CreditBalanceAdjustment(null);

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonString =
                    "{\n    \"id\": 20939,\n    \"customer\": 15460,\n    \"date\": 1410843600,\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"notes\": null,\n    \"created_at\": 1415228628\n}";

            CreditBalanceAdjustment t1 = mapper.readValue(jsonString, CreditBalanceAdjustment.class);

            assertTrue("Id is incorrect", t1.id == 20939L);
            assertTrue("Customer is incorrect", t1.customer == 15460L);
            assertTrue("Date is incorrect", t1.date == 1410843600L);
            assertTrue("Currency is incorrect", t1.currency.equals("usd"));
            assertTrue("Amount is incorrect", t1.amount == 800d);
            assertTrue("Notes is incorrect", t1.notes == null);
            assertTrue("CreatedAt is incorrect", t1.createdAt == 1415228628L);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
            fail();
        } catch (JsonMappingException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
