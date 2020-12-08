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

public class PaymentTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");


        Payment payment = conn.newPayment();
    }

    @Test
    public void testCreate() {

        // references payments_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        Payment payment = conn.newPayment();
        payment.method = "check";
        payment.reference = "1450";
        payment.amount = 800D;
        PaymentItem paymentItem = new PaymentItem();
        paymentItem.type = "invoice";
        paymentItem.amount = 800D;
        paymentItem.invoice = 44648L;
        payment.appliedTo = new PaymentItem[]{paymentItem};

        try {
            payment.create();
            assertTrue("Payment id is incorrect", payment.id == 20939);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references payments_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Payment payment = conn.newPayment();

        try {
            payment = payment.retrieve(20939);

            assertTrue("Subscription customer number is incorrect", payment.customer == 15460);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references payments_retrieve.json
        // references payments_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Payment payment = conn.newPayment();

        try {
            payment = payment.retrieve(20939);
            payment.notes = "Check was received by Jan";

            payment.save();

            assertTrue(
                    "Payment should have been updated",
                    payment.notes.equals("Check was received by Jan"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVoid() {

        // references payments_retrieve.json
        // references payments_void.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Payment payment = conn.newPayment();

        try {
            payment = payment.retrieve(20939);
            payment.voidPayment();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSend() {

        // references payments_retrieve.json
        // references payments_send_email.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Payment payment = conn.newPayment();

        EmailRequest emailRequest = new EmailRequest();

        EmailRecipient[] emailRecipients = new EmailRecipient[1];
        emailRecipients[0] = new EmailRecipient();

        emailRecipients[0].name = "Client";
        emailRecipients[0].email = "client@example.com";

        emailRequest.to = emailRecipients;
        emailRequest.subject = "Receipt for your recent payment to Dunder Mifflin, Inc.";
        emailRequest.message =
                "Dear Client, we have attached a receipt for your most recent payment. Thank you!";

        try {

            payment = payment.retrieve(20939);
            Email[] emails = payment.send(emailRequest);

            assertTrue("Email id is incorrect", emails[0].id.equals("f45382c6fbc44d44aa7f9a55eb2ce731"));

            assertTrue("Email message is incorrect", emails[0].message.equals(emailRequest.message));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {
        new Payment(null);

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonString =
                    "{\n    \"id\": 20939,\n    \"customer\": 15460,\n    \"invoice\": 44648,\n    \"date\": 1410843600,\n    \"type\": \"payment\",\n    \"method\": \"check\",\n    \"status\": \"applied\",\n    \"gateway\": null,\n    \"gateway_id\": null,\n    \"payment_source\": null,\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"notes\": null,\n    \"parent_payment\": null,\n    \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n    \"created_at\": 1415228628,\n    \"metadata\": {}\n}";

            Payment t1 = mapper.readValue(jsonString, Payment.class);

            assertTrue("Id is incorrect", t1.id == 20939L);
            assertTrue("Customer is incorrect", t1.customer == 15460L);
            assertTrue("Date is incorrect", t1.date == 1410843600L);
            assertTrue("Method is incorrect", t1.method.equals("check"));
            assertTrue("Status is succ", t1.status.equals("applied"));
            assertTrue("Reference is incorrect", t1.reference == null);
            assertTrue("Currency is incorrect", t1.currency.equals("usd"));
            assertTrue("Amount is incorrect", t1.amount == 800d);
            assertTrue("Notes is incorrect", t1.notes == null);
            assertTrue(
                    "PdfUrl is incorrect",
                    t1.pdfUrl.equals(
                            "https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf"));

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

    @Test
    public void testJsonDeserialization() {
    }
}
