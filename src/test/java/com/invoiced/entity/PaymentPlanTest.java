package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PaymentPlanTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references invoices_retrieve_3.json
        // references payment_plans_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Invoice invoice = conn.newInvoice().retrieve(998);
            PaymentPlan paymentPlan = invoice.newPaymentPlan();

            PaymentPlanInstallment[] installments = new PaymentPlanInstallment[2];
            installments[0] = new PaymentPlanInstallment();
            installments[0].date = 1234567890L;
            installments[0].amount = 1000L;
            installments[1] = new PaymentPlanInstallment();
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

        // references invoices_retrieve_3.json
        // references payment_plans_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        // references invoices_retrieve_3.json
        // references payment_plans_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        // references invoices_retrieve_3.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        try {
            Invoice invoice = conn.newInvoice().retrieve(998);
            PaymentPlan paymentPlan = invoice.newPaymentPlan();
            paymentPlan.id = 444L;
            assertTrue("Payment plan Entity id is wrong", paymentPlan.getEntityId().equals("444"));
            assertTrue("hasList property is wrong", paymentPlan.hasList() == false);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
