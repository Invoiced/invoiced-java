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

public class CustomerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer customer = conn.newCustomer();
    }

    @Test
    public void testCreate() {

        // references customers_create_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        cust.name = "Customer testCreate";
        cust.email = "testcreate@testing.org";

        try {
            cust.create();
            assertTrue("Customer Id is incorrect", cust.id == 131211L);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCustomerAPIException() {

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        try {
            cust.retrieve(198971);

        } catch (Exception e) {
            assertTrue("Should have thrown a ApiException", e.getMessage().contains("ApiException"));
        }
    }

    @Test
    public void testCustomerAuthException() {

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        try {
            cust.retrieve(198979);

        } catch (Exception e) {
            assertTrue("Should have thrown a AuthException", e.getMessage().contains("AuthException"));
        }
    }

    @Test
    public void testCustomerAPI2Exception() {

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        try {
            cust.retrieve(198980);

        } catch (Exception e) {
            assertTrue("Should have thrown a ApiException", e.getMessage().contains("ApiException"));
        }
    }

    @Test
    public void testSave() {

        // references customers_edit_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        cust.name = "Customer testSave";
        cust.email = "testcreate@testing.org";
        cust.id = 10L;
        cust.city = "Austin";

        try {
            cust.save();

            assertTrue("Customer City is incorrect", cust.city.equals("Austin"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references customers_retrieve_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        try {
            Customer cust2 = cust.retrieve(11);
            assertTrue("Customer number is incorrect", cust2.number.equals("0111"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {

        // references customers_delete_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();
        cust.id = 12L;

        try {
            cust.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBalance() {

        // references customers_retrieve_2.json
        // references customers_balance.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        try {
            cust = cust.retrieve(11);
            Balance balance = cust.getBalance();

            assertTrue("Available Credits in incorrect in balance", balance.availableCredits == 50);

            assertTrue("Available Credits in incorrect in balance", balance.totalOutstanding == 470);

            BalanceHistory[] balanceHistories = balance.history;

            assertTrue(
                    "Balance History timestamp 0 is incorrect", balanceHistories[0].timestamp == 1464041624L);

            assertTrue(
                    "Balance History timestamp 1 is incorrect", balanceHistories[1].timestamp == 1464040550L);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSendStatement() {

        // references customers_retrieve_2.json
        // references customers_send_email.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();

        EmailRequest emailRequest = new EmailRequest();

        EmailRecipient[] emailRecipients = new EmailRecipient[1];
        emailRecipients[0] = new EmailRecipient();

        emailRecipients[0].name = "Client";
        emailRecipients[0].email = "client@example.com";

        emailRequest.to = emailRecipients;
        emailRequest.subject = "Statement from Dunder Mifflin, Inc.";
        emailRequest.message =
                "Dear Client, we have attached your latest account statement. Thank you!";

        try {
            cust = cust.retrieve(11);
            Email[] emails = cust.sendStatement(emailRequest);

            assertTrue("Email id is incorrect", emails[0].id.equals("f45382c6fbc44d44aa7f9a55eb2ce731"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvoice() {

        // references customers_invoice.json
        // references customers_retrieve_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Invoice invoice = null;

        try {
            Customer cust = conn.newCustomer().retrieve(11);
            invoice = cust.invoice();

            assertTrue("Invoice id is incorrect", invoice.id == 46225L);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {
        new Customer(null);

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonInString =
                    "{\n  \"id\": 15444,\n  \"name\": \"Acme\",\n  \"number\": \"CUST-0001\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": true,\n  \"payment_terms\": null,\n  \"payment_source\": {\n    \"id\": 850,\n    \"object\": \"card\",\n    \"brand\": \"Visa\",\n    \"last4\": \"4242\",\n    \"exp_month\": 2,\n    \"exp_year\": 20,\n    \"funding\": \"credit\"\n  },\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": null,\n  \"address1\": null,\n  \"address2\": null,\n  \"city\": null,\n  \"state\": null,\n  \"postal_code\": null,\n  \"country\": \"US\",\n  \"tax_id\": null,\n  \"phone\": null,\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

            Customer c1 = mapper.readValue(jsonInString, Customer.class);

            assertTrue("Id is incorrect", c1.id == 15444L);
            assertTrue("Name is incorrect", c1.name.equals("Acme"));
            assertTrue("Number is incorrect", c1.number.equals("CUST-0001"));
            assertTrue("Email is incorrect", c1.email.equals("billing@acmecorp.com"));
            assertTrue("PaymentTerms is incorrect", c1.paymentTerms == null);
            assertTrue("PaymentSource is incorrect", c1.paymentSource != null);
            assertTrue("Taxes is incorrect", c1.taxes.length == 0);
            assertTrue("Type is incorrect", c1.type.equals("company"));
            assertTrue("AttentionTo is incorrect", c1.attentionTo == null);
            assertTrue("Address1 is incorrect", c1.address1 == null);
            assertTrue("Address2 is incorrect", c1.address2 == null);
            assertTrue("City is incorrect", c1.city == null);
            assertTrue("State is incorrect", c1.state == null);
            assertTrue("PostalCode is incorrect", c1.postalCode == null);
            assertTrue("Country is incorrect", c1.country.equals("US"));
            assertTrue("TaxID is incorrect", c1.taxId == null);
            assertTrue("Phone is incorrect", c1.phone == null);
            assertTrue("Notes is incorrect", c1.notes == null);
            assertTrue(
                    "statementPdfUrl is incorrect",
                    c1.statementPdfUrl.equals(
                            "https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf"));
            assertTrue("CreatedAt is incorrect", c1.createdAt == 1415222128L);
            assertTrue("Metadata is incorrect", c1.metadata != null);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
            fail();
        } catch (JsonMappingException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testConsolidateInvoicesNoArg() {

        // references consolidate_invoices.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();
        cust.id = 15444L;

        try {

            Invoice consolInvoice = cust.consolidateInvoices();

            assertTrue("Invoice id is incorrect", consolInvoice.id == 46226);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConsolidateInvoicesWithArg() {

        // references consolidate_invoices.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();
        cust.id = 15444L;

        try {

            Invoice consolInvoice = cust.consolidateInvoices(1234567890L);

            assertTrue("Invoice id is incorrect", consolInvoice.id == 46226);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSendStatementText() {

        // references customers_retrieve_2.json
        // references customers_send_text_message.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();
        cust.id = 11L;

        TextRequest textRequest = new TextRequest();

        TextRecipient[] textRecipients = new TextRecipient[1];
        textRecipients[0] = new TextRecipient();

        textRecipients[0].name = "Bob Smith";
        textRecipients[0].phone = "+15125551212";

        textRequest.to = textRecipients;
        textRequest.message = "Texting!";

        try {
            TextMessage[] textMessages = cust.sendStatementText(textRequest);

            assertTrue("Text id is incorrect", textMessages[0].id.equals("a1b2c3"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSendStatementLetter() {

        // references customers_retrieve_2.json
        // references customers_send_letter.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();
        cust.id = 11L;

        LetterRequest letterRequest = new LetterRequest();

        letterRequest.type = "open_item";

        try {
            Letter letter = cust.sendStatementLetter(letterRequest);

            assertTrue("Letter id is incorrect", letter.id.equals("c3b2a1"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNewNote() {

        // references notes_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer();
        cust.id = 11L;

        Note note = cust.newNote();
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
    public void testTaxes() {
        //src/test/resources/mappings/customers_edit_3.json
        //src/test/resources/mappings/tax_rates_retrieve_2.json

        Connection conn = new Connection("api_key", "http://localhost:8080");

        try {
            Customer cust = conn.newCustomer().retrieve(11);
            TaxRate taxRate1 = conn.newTaxRate().retrieve("vat");
            TaxRate taxRate2 = conn.newTaxRate().retrieve("vat2");
            cust.taxes = new TaxRate[] { taxRate1, taxRate2 };
            cust.save();
            assertTrue("Customer Tax is incorrect", cust.taxes[0].toString().equals("vat"));
            assertTrue("Customer Tax is incorrect", cust.taxes[1].toString().equals("vat2"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
