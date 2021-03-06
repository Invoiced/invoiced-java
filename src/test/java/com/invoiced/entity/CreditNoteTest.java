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

public class CreditNoteTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();
    }

    @Test
    public void testCreate() {

        // references credit_notes_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();

        creditNote.customer = 15444L;
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
        creditNote.items = items;
        creditNote.taxes = taxes;

        try {

            creditNote.create();

            assertTrue("Credit note Id is incorrect", creditNote.id == 2048L);

            assertTrue("Credit note Item Id is incorrect", creditNote.items[0].id == 7);

            assertTrue("Credit note Item Id is incorrect", creditNote.items[1].id == 8);

            assertTrue("Tax Id is incorrect", creditNote.taxes[0].id == 20554);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references credit_notes_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();

        try {
            creditNote = creditNote.retrieve(2048);
            assertTrue("Credit note currency is incorrect", creditNote.currency.equals("usd"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references credit_notes_retrieve.json
        // references credit_notes_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();

        try {
            creditNote = creditNote.retrieve(2048);
            creditNote.name = "Comp";

            creditNote.save();

            assertTrue("Credit note name should have changed", creditNote.name.equals("Comp"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // }

    @Test
    public void testDelete() {

        // references credit_notes_retrieve.json
        // references credit_notes_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();

        try {
            creditNote = creditNote.retrieve(2048);
            creditNote.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testList() {

        // references credit_notes_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<CreditNote> creditNotes = conn.newCreditNote().listAll();

            assertTrue("Credit note 1 id is incorrect", creditNotes.get(0).id == 2048L);

            assertTrue("Credit note 2 id is incorrect", creditNotes.get(1).id == 2049L);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSendCreditNoteEmail() {

        // references credit_notes_retrieve.json
        // references credit_notes_send_email.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();

        EmailRequest emailRequest = new EmailRequest();

        EmailRecipient[] emailRecipients = new EmailRecipient[1];
        emailRecipients[0] = new EmailRecipient();

        emailRecipients[0].name = "Client";
        emailRecipients[0].email = "client@example.com";

        emailRequest.to = emailRecipients;
        emailRequest.subject = "test";
        emailRequest.message = "credit note email example";

        try {
            creditNote = creditNote.retrieve(2048);
            Email[] emails = creditNote.send(emailRequest);

            assertTrue("Email id is incorrect", emails[0].id.equals("30e4ffaf5a426bf0a381c4d4e32f6f4g"));

            assertTrue("Email message is incorrect", emails[0].message.equals(emailRequest.message));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testListAttachments() {

        // references credit_notes_retrieve.json
        // references credit_notes_list_attachments.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();

        try {
            creditNote = creditNote.retrieve(2048);

            Attachment[] attachments = creditNote.listAttachments();

            assertTrue("Attachment 0 id is incorrect", attachments[0].id == 14);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVoidCreditNote() {

        // references credit_notes_void.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        CreditNote creditNote = conn.newCreditNote();
        creditNote.id = 2048L;

        try {
            creditNote.voidCreditNote();

            assertTrue("Credit note status should be voided", creditNote.status.equals("voided"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {
        new CreditNote(null);

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonInString =
                    "{\"id\":2048,\"object\":\"credit_note\",\"customer\":15444,\"invoice\":46225,\"name\":null,\"currency\":\"usd\",\"draft\":false,\"closed\":false,\"paid\":false,\"status\":\"open\",\"number\":\"CN-0016\",\"date\":1416290400,\"items\":[{\"id\":7,\"object\":\"line_item\",\"catalog_item\":null,\"type\":\"product\",\"name\":\"Copy Paper, Case\",\"description\":null,\"quantity\":1,\"unit_cost\":45,\"amount\":45,\"discountable\":true,\"discounts\":[],\"taxable\":true,\"taxes\":[],\"metadata\":{}},{\"id\":8,\"object\":\"line_item\",\"catalog_item\":\"delivery\",\"type\":\"service\",\"name\":\"Delivery\",\"description\":null,\"quantity\":1,\"unit_cost\":10,\"amount\":10,\"discountable\":true,\"discounts\":[],\"taxable\":true,\"taxes\":[],\"metadata\":{}}],\"notes\":null,\"subtotal\":55,\"discounts\":[],\"taxes\":[{\"id\":20554,\"object\":\"tax\",\"amount\":3.85,\"tax_rate\":null}],\"total\":51.15,\"balance\":51.15,\"url\":\"https://dundermifflin.invoiced.com/credit_notes/IZmXbVOPyvfD3GPBmyd6FwXY\",\"pdf_url\":\"https://dundermifflin.invoiced.com/credit_notes/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\"created_at\":1415229884,\"metadata\":{}}";

            CreditNote i1 = mapper.readValue(jsonInString, CreditNote.class);

            assertTrue("Id is incorrect", i1.id == 2048L);
            assertTrue("Customer is incorrect", i1.customer == 15444L);
            assertTrue("Name is incorrect", i1.name == null);
            assertTrue("Object is credit_note", i1.name == null);
            assertTrue("Currency is incorrect", i1.currency.equals("usd"));
            assertTrue("Draft is incorrect", i1.draft == false);
            assertTrue("Closed is incorrect", i1.closed == false);
            assertTrue("Paid is incorrect", i1.paid == false);
            assertTrue("Status is incorrect", i1.status.equals("open"));

            assertTrue("Number is incorrect", i1.number.equals("CN-0016"));

            assertTrue("Date is incorrect", i1.date == 1416290400L);

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
}
