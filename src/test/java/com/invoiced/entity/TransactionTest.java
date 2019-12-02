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

public class TransactionTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testParentID() {
		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();
		assertTrue("Invoice Parent Id is incorrect", transaction.getParentID() == null);
		transaction.setParentID("-4");
		assertTrue("Invoice Parent Id is incorrect", transaction.getParentID() == null);

	}

	@Test
	public void testCreate() {

		// references connection_rr_21.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();

		transaction.invoice = 44648;
		transaction.method = "check";
		transaction.gatewayId = "1450";
		transaction.amount = 800;

		try {

			transaction.create();
			assertTrue("Transaction id is incorrect", transaction.id == 20939);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_22.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();

		try {
			transaction = transaction.retrieve(20939);

			assertTrue("Subscription customer number is incorrect", transaction.customer == 15460);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_22.json
		// references connection_rr_23.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();

		try {
			transaction = transaction.retrieve(20939);
			transaction.notes = "Check was received by Jan";

			transaction.save();

			assertTrue("Transaction should have been updated", transaction.notes.equals("Check was received by Jan"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_22.json
		// references connection_rr_24.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();

		try {
			transaction = transaction.retrieve(20939);
			transaction.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSend() {

		// references connection_rr_22.json
		// references connection_rr_31.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();

		EmailRequest emailRequest = new EmailRequest();

		EmailRecipient[] emailRecipients = new EmailRecipient[1];
		emailRecipients[0] = new EmailRecipient();

		emailRecipients[0].name = "Client";
		emailRecipients[0].email = "client@example.com";

		emailRequest.to = emailRecipients;
		emailRequest.subject = "Receipt for your recent payment to Dunder Mifflin, Inc.";
		emailRequest.message = "Dear Client, we have attached a receipt for your most recent payment. Thank you!";

		try {

			transaction = transaction.retrieve(20939);
			Email[] emails = transaction.send(emailRequest);

			assertTrue("Email id is incorrect", emails[0].id.equals("f45382c6fbc44d44aa7f9a55eb2ce731"));

			assertTrue("Email message is incorrect", emails[0].message.equals(emailRequest.message));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRefund() {

		// references connection_rr_22.json
		// references connection_rr_32.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Transaction transaction = conn.newTransaction();

		try {

			transaction = transaction.retrieve(20939);

			Transaction refund = transaction.refund(400);

			assertTrue("Refund Transaction id is incorrect", refund.id == 20952);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testInitiateCharge() {

		// references connection_rr_76.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		ChargeRequest chargeRequest = new ChargeRequest();

		chargeRequest.customer = 401558;
		chargeRequest.currency = "usd";
		chargeRequest.amount = 2000;

		ChargeSplit[] splits = new ChargeSplit[1];
		splits[0] = new ChargeSplit();
		
		splits[0].type = "invoice";
		splits[0].invoice = 1234;
		splits[0].amount = 2000;

		chargeRequest.splits = splits;

		try {

			Transaction charge = conn.newTransaction().initiateCharge(chargeRequest);

			assertTrue("Charge transaction id is incorrect", charge.id == 1234);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testJsonSerialization() {
		new Transaction(null);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonString = "{\n    \"id\": 20939,\n    \"customer\": 15460,\n    \"invoice\": 44648,\n    \"date\": 1410843600,\n    \"type\": \"payment\",\n    \"method\": \"check\",\n    \"status\": \"succeeded\",\n    \"gateway\": null,\n    \"gateway_id\": null,\n    \"payment_source\": null,\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"fee\": 0,\n    \"notes\": null,\n    \"parent_transaction\": null,\n    \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n    \"created_at\": 1415228628,\n    \"metadata\": {}\n}";

			Transaction t1 = mapper.readValue(jsonString, Transaction.class);

			assertTrue("Id is incorrect", t1.id == 20939L);
			assertTrue("Customer is incorrect", t1.customer == 15460L);
			assertTrue("Invoice is incorrect", t1.invoice == 44648L);
			assertTrue("Date is incorrect", t1.date == 1410843600L);
			assertTrue("Type is incorrect", t1.type.equals("payment"));
			assertTrue("Method is incorrect", t1.method.equals("check"));
			assertTrue("Status is succ", t1.status.equals("succeeded"));
			assertTrue("Gateway is incorrect", t1.gateway == null);
			assertTrue("Gateway id is incorrect", t1.gatewayId == null);
			assertTrue("Payment source is incorrect", t1.paymentSource == null);
			assertTrue("Currency is incorrect", t1.currency.equals("usd"));
			assertTrue("Amount is incorrect", t1.amount == 800d);
			assertTrue("Fee is incorrect", t1.fee == 0d);
			assertTrue("Notes is incorrect", t1.notes == null);
			assertTrue("Parent Transaction is incorrect", t1.parentTransaction == 0L);
			assertTrue("PdfUrl is incorrect",
			           t1.pdfUrl.equals("https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf"));

			assertTrue("CreatedAt is incorrect", t1.createdAt == 1415228628L);
			assertTrue("Metadata is incorrect", t1.metadata != null);

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