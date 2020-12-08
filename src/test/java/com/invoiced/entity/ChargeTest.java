package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChargeTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");
        Charge charge = conn.newCharge();
    }

    @Test
    public void testCreate() {

        // references charges_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        ChargeRequest chargeRequest = new ChargeRequest();

        chargeRequest.customer = 401558L;
        chargeRequest.currency = "usd";
        chargeRequest.amount = 2000D;

        PaymentItem[] splits = new PaymentItem[1];
        splits[0] = new PaymentItem();

        splits[0].type = "invoice";
        splits[0].invoice = 1234L;
        splits[0].amount = 2000D;

        chargeRequest.appliedTo = splits;

        try {
            Payment payment = conn.newCharge().create(chargeRequest);

            assertTrue("Charge id is incorrect", payment.charge.id == 46374);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
