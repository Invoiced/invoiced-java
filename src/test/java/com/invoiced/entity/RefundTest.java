package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RefundTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");
        Charge charge = conn.newCharge();
    }

    @Test
    public void testCreate() {

        // references refunds_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");
        Refund refund = conn.newRefund();

        try {
            refund.create(123, 800);

            assertTrue("Refund id is incorrect", refund.id == 5309);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
