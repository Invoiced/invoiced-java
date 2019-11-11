package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class CustomerTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testParentID() {
		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer customer = conn.newCustomer();
		assertTrue("Customer Parent Id is incorrect", customer.getParentID() == -1);
		customer.setParentID(-4);
		assertTrue("Customer Parent Id is incorrect", customer.getParentID() == -1);

	}

	@Test
	public void testCreate() {

		// references connection_rr_9.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

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

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();

		try {
			cust.retrieve(198971);

		} catch (Exception e) {
			assertTrue("Should have thrown a ApiException", e.getMessage().contains("ApiException"));
		}

	}

	@Test
	public void testCustomerAuthException() {

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();

		try {
			cust.retrieve(198979);

		} catch (Exception e) {
			assertTrue("Should have thrown a AuthException", e.getMessage().contains("AuthException"));
		}

	}

	@Test
	public void testCustomerAPI2Exception() {

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();

		try {
			cust.retrieve(198980);

		} catch (Exception e) {
			assertTrue("Should have thrown a ApiException", e.getMessage().contains("ApiException"));
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_10.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();

		cust.name = "Customer testSave";
		cust.email = "testcreate@testing.org";
		cust.id = 10;
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

		// references connection_rr_11.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

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

		// references connection_rr_12.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();
		cust.id = 12;

		try {
			cust.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testBalance() {

		// references connection_rr_11.json
		// references connection_rr_25.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();

		try {
			cust = cust.retrieve(11);
			Balance balance = cust.getBalance();

			assertTrue("Available Credits in incorrect in balance", balance.availableCredits == 50);

			assertTrue("Available Credits in incorrect in balance", balance.totalOutstanding == 470);

			BalanceHistory[] balanceHistories = balance.history;

			assertTrue("Balance History timestamp 0 is incorrect",
			           balanceHistories[0].timestamp  == 1464041624L);

			assertTrue("Balance History timestamp 1 is incorrect",
			           balanceHistories[1].timestamp  == 1464040550L);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSendStatement() {

		// references connection_rr_11.json
		// references connection_rr_26.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();

		EmailRequest emailRequest = new EmailRequest();

		EmailRecipient[] emailRecipients = new EmailRecipient[1];
		emailRecipients[0] = new EmailRecipient();

		emailRecipients[0].name = "Client";
		emailRecipients[0].email = "client@example.com";

		emailRequest.to = emailRecipients;
		emailRequest.subject = "Statement from Dunder Mifflin, Inc.";
		emailRequest.message = "Dear Client, we have attached your latest account statement. Thank you!";

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

		// references connection_rr_41.json
		// references connection_rr_11.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

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

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonInString = "{\n  \"id\": 15444,\n  \"name\": \"Acme\",\n  \"number\": \"CUST-0001\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": true,\n  \"payment_terms\": null,\n  \"payment_source\": {\n    \"id\": 850,\n    \"object\": \"card\",\n    \"brand\": \"Visa\",\n    \"last4\": \"4242\",\n    \"exp_month\": 2,\n    \"exp_year\": 20,\n    \"funding\": \"credit\"\n  },\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": null,\n  \"address1\": null,\n  \"address2\": null,\n  \"city\": null,\n  \"state\": null,\n  \"postal_code\": null,\n  \"country\": \"US\",\n  \"tax_id\": null,\n  \"phone\": null,\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

			Customer c1 = mapper.readValue(jsonInString, Customer.class);

			assertTrue("Id is incorrect", c1.id == 15444L);
			assertTrue("Name is incorrect", c1.name.equals("Acme"));
			assertTrue("Number is incorrect", c1.number.equals("CUST-0001"));
			assertTrue("Email is incorrect", c1.email.equals("billing@acmecorp.com"));
			assertTrue("PaymentTerms is incorrect", c1.paymentTerms == null);
			// TODO TEST PAYMENT SOURCE MORE
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
			assertTrue("statementPdfUrl is incorrect", c1.statementPdfUrl
			           .equals("https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf"));
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

		// references connection_rr_13.json
		// references connection_rr_59.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Customer cust = conn.newCustomer();
		cust.id = 15444L;
		
		try {

			Invoice consolInvoice = cust.consolidateInvoices();

			assertTrue("Invoice id is incorrect", consolInvoice.id == 46226);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}