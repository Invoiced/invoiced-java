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

public class InvoiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();
    }

    @Test
    public void testCreate() {

        // references invoices_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        invoice.customer = 15444L;
        invoice.paymentTerms = "NET 14";
        LineItem[] items = new LineItem[2];
        items[0] = new LineItem();
        items[0].name = "Copy paper, Case";
        items[0].quantity = 1D;
        items[0].unitCost = 45D;
        items[1] = new LineItem();
        items[1].item = "delivery";
        items[1].quantity = 1D;
        Tax[] taxes = new Tax[1];
        taxes[0] = new Tax();
        taxes[0].amount = 3.85D;
        invoice.items = items;
        invoice.taxes = taxes;

        try {

            invoice.create();

            assertTrue("Invoice Id is incorrect", invoice.id == 46225L);

            assertTrue("Invoice Item Id is incorrect", invoice.items[0].id == 7);

            assertTrue("Invoice Item Id is incorrect", invoice.items[1].id == 8);

            assertTrue("Tax Id is incorrect", invoice.taxes[0].id == 20554);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references invoices_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        try {
            invoice = invoice.retrieve(46225);
            assertTrue(
                    "Invoice url is incorrect",
                    invoice.url.equals(
                            "https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references invoices_retrieve.json
        // references invoices_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        try {
            invoice = invoice.retrieve(46225);
            invoice.name = "July Paper Delivery";
            invoice.notes = "The order was delivered on Jul 20, 2015";

            invoice.save();

            assertTrue("Invoice status should be sent", invoice.status.equals("sent"));

            assertTrue("Invoice name should be updated", invoice.name.equals("July Paper Delivery"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // }

    @Test
    public void testDelete() {

        // references invoices_retrieve.json
        // references invoices_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        try {
            invoice = invoice.retrieve(46225);
            invoice.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPay() {

        // references invoices_pay.json
        // references invoices_retrieve_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        try {
            invoice = invoice.retrieve(46226);

            assertTrue("Invoice should not be paid", invoice.paid == false);

            invoice.pay();

            assertTrue("Invoice should be paid", invoice.paid == true);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testListAttachments() {

        // references invoices_retrieve.json
        // references invoices_list_attachments.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        try {
            invoice = invoice.retrieve(46225);

            Attachment[] attachments = invoice.listAttachments();

            assertTrue("Attachment 0 id is incorrect", attachments[0].id == 13);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSend() {

        // references invoices_retrieve.json
        // references invoices_send_email.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        EmailRequest emailRequest = new EmailRequest();

        EmailRecipient[] emailRecipients = new EmailRecipient[1];
        emailRecipients[0] = new EmailRecipient();

        emailRecipients[0].name = "Client";
        emailRecipients[0].email = "client@example.com";

        emailRequest.to = emailRecipients;
        emailRequest.subject = "New Invoice from Dunder Mifflin, Inc.: INV-0016";
        emailRequest.message =
                "Dear Client, a new invoice for $51.15 has been created on your account. [View and Pay Invoice button] Thank you!";

        try {
            invoice = invoice.retrieve(46225);
            Email[] emails = invoice.send(emailRequest);

            assertTrue("Email id is incorrect", emails[0].id.equals("f45382c6fbc44d44aa7f9a55eb2ce731"));

            assertTrue("Email message is incorrect", emails[0].message.equals(emailRequest.message));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSendText() {

        // references invoices_retrieve.json
        // references invoices_send_text_message.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        TextRequest textRequest = new TextRequest();

        TextRecipient[] textRecipients = new TextRecipient[1];
        textRecipients[0] = new TextRecipient();

        textRecipients[0].name = "Bob Smith";
        textRecipients[0].phone = "+15125551212";

        textRequest.to = textRecipients;
        textRequest.message = "Texting!";

        try {
            invoice = invoice.retrieve(46225);
            TextMessage[] textMessages = invoice.sendText(textRequest);

            assertTrue("Text id is incorrect", textMessages[0].id.equals("d4e5f6"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSendLetter() {

        // references invoices_retrieve.json
        // references invoices_send_letter.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();

        try {
            invoice = invoice.retrieve(46225);
            Letter letter = invoice.sendLetter();

            assertTrue("Letter id is incorrect", letter.id.equals("f6e5d4"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNewNote() {

        // references notes_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();
        invoice.customer = 11L;

        Note note = invoice.newNote();
        note.notes = "example note";

        try {
            note.create();

            assertTrue("Note id is incorrect", note.id == 1212);
            assertTrue("Note customer id is incorrect", note.customer == 11);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVoidInvoice() {

        // references invoices_void.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = conn.newInvoice();
        invoice.id = 46701L;

        try {
            invoice.voidInvoice();

            assertTrue("Invoice should be voided", invoice.status.equals("voided"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {
        new Invoice(null);

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonInString =
                    "{\n  \"id\": 46225,\n  \"customer\": 15444,\n  \"name\": null,\n  \"currency\": \"usd\",\n  \"draft\": false,\n  \"closed\": false,\n  \"paid\": true,\n  \"status\": \"not_sent\",\n  \"chase\": false,\n  \"next_chase_on\": null,\n  \"autopay\": false,\n  \"attempt_count\": 0,\n  \"next_payment_attempt\": null,\n  \"subscription\": null,\n  \"number\": \"INV-0016\",\n  \"date\": 1416290400,\n  \"due_date\": 1417500000,\n  \"payment_terms\": \"NET 14\",\n  \"items\": [\n    {\n      \"id\": 7,\n      \"catalog_item\": null,\n      \"type\": \"product\",\n      \"name\": \"Copy Paper, Case\",\n      \"description\": null,\n      \"quantity\": 1,\n      \"unit_cost\": 45,\n      \"amount\": 45,\n      \"discountable\": true,\n      \"discounts\": [],\n      \"taxable\": true,\n      \"taxes\": [],\n      \"metadata\": {}\n    },\n    {\n      \"id\": 8,\n      \"catalog_item\": \"delivery\",\n      \"type\": \"service\",\n      \"name\": \"Delivery\",\n      \"description\": \"\",\n      \"quantity\": 1,\n      \"unit_cost\": 10,\n      \"amount\": 10,\n      \"discountable\": true,\n      \"discounts\": [],\n      \"taxable\": true,\n      \"taxes\": [],\n      \"metadata\": {}\n    }\n  ],\n  \"notes\": null,\n  \"subtotal\": 55,\n  \"discounts\": [],\n  \"taxes\": [\n    {\n      \"id\": 20554,\n      \"amount\": 3.85,\n      \"tax_rate\": null\n    }\n  ],\n  \"total\": 51.15,\n  \"balance\": 51.15,\n  \"tags\": [],\n  \"url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY\",\n  \"payment_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/payment\",\n  \"pdf_url\": \"https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n  \"created_at\": 1415229884,\n  \"metadata\": {}\n}";

            Invoice i1 = mapper.readValue(jsonInString, Invoice.class);

            assertTrue("Id is incorrect", i1.id == 46225L);
            assertTrue("Customer is incorrect", i1.customer == 15444L);
            assertTrue("Name is incorrect", i1.name == null);
            assertTrue("Currency is incorrect", i1.currency.equals("usd"));
            assertTrue("Draft is incorrect", i1.draft == false);
            assertTrue("Closed is incorrect", i1.closed == false);
            assertTrue("Paid is incorrect", i1.paid == true);
            assertTrue("Status is incorrect", i1.status.equals("not_sent"));
            assertTrue("Autopay is incorrect", i1.autopay == false);

            assertTrue("Attempt count is incorrect", i1.attemptCount == 0);
            assertTrue("Next Payment Attempt is incorrect", i1.nextPaymentAttempt == null);

            assertTrue("Subscription is incorrect", i1.subscription == null);

            assertTrue("Number is incorrect", i1.number.equals("INV-0016"));

            assertTrue("Date is incorrect", i1.date == 1416290400L);
            assertTrue("Due Date is incorrect", i1.dueDate == 1417500000L);

            assertTrue("Payment Terms is incorrect", i1.paymentTerms.equals("NET 14"));
            assertTrue("Items is incorrect", i1.items.length > 0);

            assertTrue("Notes is incorrect", i1.notes == null);
            assertTrue("Subtotal is incorrect", i1.subtotal == 55d);
            assertTrue("Discounts is incorrect", i1.discounts.length == 0);
            assertTrue("Taxes is incorrect", i1.taxes.length > 0);
            assertTrue("Total is incorrect", i1.total == 51.15d);
            assertTrue("Balance is incorrect", i1.balance == 51.15d);

            assertTrue(
                    "Url is incorrect",
                    i1.url.equals("https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY"));

            assertTrue(
                    "Payment Url is incorrect",
                    i1.paymentUrl.equals(
                            "https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/payment"));

            assertTrue(
                    "Pdf Url is Incorrect",
                    i1.pdfUrl.equals(
                            "https://dundermifflin.invoiced.com/invoices/IZmXbVOPyvfD3GPBmyd6FwXY/pdf"));

            assertTrue("Create At is incorrect", i1.createdAt == 1415229884L);

            assertTrue("Metadata is incorrect", i1.metadata != null);

            assertTrue("There should be 2 items", i1.items.length == 2);

            assertTrue("Line item amount should be 45.0", i1.items[0].amount == 45.0);

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
