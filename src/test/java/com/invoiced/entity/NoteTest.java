package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class NoteTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testProtectedMethods() {

		Connection conn = new Connection("", true);
		conn.testModeOn();
		Note note = conn.newNote();
		note.id = 312;
		assertTrue("Note Entity id is wrong", note.getEntityId() == 312);
		assertTrue("Note id is wrong", note.getParentID() == -1);
		try {
			note.setParentID(-1231);
			assertTrue("Note id is wrong", note.getParentID() == -1);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testList() {

		// references connection_rr_77.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Note note = conn.newNote();
			EntityList<Note> notes = note.listAll();

			assertTrue("Total count is incorrect", notes.getTotalCount() == 2);
			assertTrue("Id1 is incorrect", notes.get(0).id == 501L);
			assertTrue("Id2 is incorrect", notes.get(1).id == 502L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testRetrieveCustomerNotes() {

		// references connection_rr_11.json
		// references connection_rr_115.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Customer cust = conn.newCustomer().retrieve(11);			
			Note note = cust.newNote();

			assertTrue("note customerId not set", note.customerId == 11);

			// test if endpoint generates correctly in the abstract
			note.currentOperation = "listAll";
			assertTrue("endpoint is incorrect", note.getEntityName().equals("customers/11/notes"));
			note.currentOperation = null;

			// test an actual call
			EntityList<Note> notes = note.listAll();

			assertTrue("Total count is incorrect", notes.getTotalCount() == 2);
			assertTrue("Id1 is incorrect", notes.get(0).id == 50001L);
			assertTrue("Id2 is incorrect", notes.get(1).id == 50004L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRetrieveInvoiceNotes() {

		// references connection_rr_14.json
		// references connection_rr_116.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Invoice invoice = conn.newInvoice().retrieve(46225);			
			Note note = invoice.newNote();

			assertTrue("note invoiceId not set", note.invoiceId == 46225);

			// test if endpoint generates correctly in the abstract
			note.currentOperation = "listAll";
			assertTrue("endpoint is incorrect", note.getEntityName().equals("invoices/46225/notes"));
			note.currentOperation = null;

			// test an actual call
			EntityList<Note> notes = note.listAll();

			assertTrue("Total count is incorrect", notes.getTotalCount() == 2);
			assertTrue("Id1 is incorrect", notes.get(0).id == 50011L);
			assertTrue("Id2 is incorrect", notes.get(1).id == 50013L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}