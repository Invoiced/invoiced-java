package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EstimateTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();
    }

    @Test
    public void testCreate() {

        // references connection_rr_63.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();

        estimate.customer = 15444L;
        estimate.paymentTerms = "NET 30";
        LineItem[] items = new LineItem[2];
        items[0] = new LineItem();
        items[0].name = "Copy paper, Case";
        items[0].quantity = 1D;
        items[0].unitCost = 50D;
        items[1] = new LineItem();
        items[1].catalogItem = "delivery";
        items[1].quantity = 1D;
        Tax[] taxes = new Tax[1];
        taxes[0] = new Tax();
        taxes[0].amount = 3.85D;
        estimate.items = items;
        estimate.taxes = taxes;

        try {

            estimate.create();

            assertTrue("Estimate Id is incorrect", estimate.id == 46999L);

            assertTrue("Estimate Item Id is incorrect", estimate.items[0].id == 7);

            assertTrue("Estimate Item Id is incorrect", estimate.items[1].id == 8);

            assertTrue("Tax Id is incorrect", estimate.taxes[0].id == 20554);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references connection_rr_64.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();

        try {
            estimate = estimate.retrieve(46999);
            assertTrue("Estimate currency is incorrect", estimate.currency.equals("usd"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references connection_rr_64.json
        // references connection_rr_65.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();

        try {
            estimate = estimate.retrieve(46999);
            estimate.name = "July Paper Delivery";
            estimate.status = "sent";

            estimate.save();

            assertTrue("Estimate status should be sent", estimate.status.equals("sent"));

            assertTrue("Estimate name should be updated", estimate.name.equals("July Paper Delivery"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // }

    @Test
    public void testDelete() {

        // references connection_rr_64.json
        // references connection_rr_66.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();

        try {
            estimate = estimate.retrieve(46999);
            estimate.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testList() {

        // references connection_rr_67.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        try {
            EntityList<Estimate> estimates = conn.newEstimate().listAll();

            assertTrue("Estimate 1 id is incorrect", estimates.get(0).id == 46999L);

            assertTrue("Estimate 2 id is incorrect", estimates.get(1).id == 46700L);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSendEstimateEmail() {

        // references connection_rr_72.json
        // references connection_rr_73.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();

        EmailRequest emailRequest = new EmailRequest();

        EmailRecipient[] emailRecipients = new EmailRecipient[1];
        emailRecipients[0] = new EmailRecipient();

        emailRecipients[0].name = "Client";
        emailRecipients[0].email = "client@example.com";

        emailRequest.to = emailRecipients;
        emailRequest.subject = "test";
        emailRequest.message = "estimate email example";

        try {
            estimate = estimate.retrieve(11641);
            Email[] emails = estimate.send(emailRequest);

            assertTrue("Email id is incorrect", emails[0].id.equals("30e4ffaf5a426bf0a381c4d4e32f6f4f"));

            assertTrue("Email message is incorrect", emails[0].message.equals(emailRequest.message));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVoidEstimate() {

        // references connection_rr_71.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();
        estimate.id = 11641;

        try {
            estimate.voidEstimate();

            assertTrue("Estimate status should be voided", estimate.status.equals("voided"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConvertToInvoice() {

        // references connection_rr_74.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        Estimate estimate = conn.newEstimate();
        estimate.id = 11641;

        try {
            Invoice invoice = estimate.invoice();

            assertTrue("Invoice id is incorrect", invoice.id == 2339090);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
