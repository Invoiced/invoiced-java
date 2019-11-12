package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class PaymentPlanTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_91.json
		// references connection_rr_92.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Invoice invoice = conn.newInvoice().retrieve(998);
            PaymentPlan paymentPlan = invoice.newPaymentPlan();

            Installment[] installments = new Installment[2];
            installments[0] = new Installment();
            installments[0].date = 1234567890L;
            installments[0].amount = 1000L;
            installments[1] = new Installment();
            installments[0].date = 1234567891L;
            installments[0].amount = 1000L;
            paymentPlan.installments = installments;
            paymentPlan.create();
            
			assertTrue("Payment plan id is incorrect", paymentPlan.id == 99);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

        // references connection_rr_91.json
		// references connection_rr_93.json

		Connection conn = new Connection("", true);
        conn.testModeOn();

		try {
            Invoice invoice = conn.newInvoice().retrieve(998);
            PaymentPlan paymentPlan = invoice.newPaymentPlan().retrieve();

			assertTrue("Status is incorrect", paymentPlan.status.equals("active"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testCancel() {

        // references connection_rr_91.json
		// references connection_rr_94.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
            Invoice invoice = conn.newInvoice().retrieve(998);
            PaymentPlan paymentPlan = invoice.newPaymentPlan().retrieve();
			paymentPlan.cancel();

		} catch (Exception e) {
			fail(e.getMessage());
		}

    }
    
    @Test
	public void testProtectedMethods() {

        // references connection_rr_91.json

		Connection conn = new Connection("", true);
        conn.testModeOn();
		try {
            Invoice invoice = conn.newInvoice().retrieve(998);
            PaymentPlan paymentPlan = invoice.newPaymentPlan();
            paymentPlan.id = 444;
            assertTrue("Payment plan Entity id is wrong", paymentPlan.getEntityId() == 444);
            assertTrue("Payment plan id is wrong", paymentPlan.getParentID() == 998);
			paymentPlan.setParentID(445);
            assertTrue("Payment plan id is wrong", paymentPlan.getParentID() == 445);
            assertTrue("hasList property is wrong", paymentPlan.hasList() == false);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}