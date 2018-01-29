
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

public class CreditNoteTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testParentID() {
		Connection conn = new Connection("", true);
		conn.testModeOn();

		CreditNote creditNote = conn.newCreditNote();
		assertTrue("Credit Note Parent Id is incorrect", creditNote .getParentID() == -1);
		creditNote.setParentID(-4);
		assertTrue("Credit Note Parent Id is incorrect", creditNote .getParentID() == -1);

	}



	@Test
	public void testJsonSerialization() {
		new CreditNote(null);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonInString = "{\"id\":2048,\"object\":\"credit_note\",\"customer\":15444,\"invoice\":46225,\"name\":null,\"currency\":\"usd\",\"draft\":false,\"closed\":false,\"paid\":false,\"status\":\"open\",\"number\":\"CN-0016\",\"date\":1416290400,\"items\":[{\"id\":7,\"object\":\"line_item\",\"catalog_item\":null,\"type\":\"product\",\"name\":\"Copy Paper, Case\",\"description\":null,\"quantity\":1,\"unit_cost\":45,\"amount\":45,\"discountable\":true,\"discounts\":[],\"taxable\":true,\"taxes\":[],\"metadata\":{}},{\"id\":8,\"object\":\"line_item\",\"catalog_item\":\"delivery\",\"type\":\"service\",\"name\":\"Delivery\",\"description\":null,\"quantity\":1,\"unit_cost\":10,\"amount\":10,\"discountable\":true,\"discounts\":[],\"taxable\":true,\"taxes\":[],\"metadata\":{}}],\"notes\":null,\"subtotal\":55,\"discounts\":[],\"taxes\":[{\"id\":20554,\"object\":\"tax\",\"amount\":3.85,\"tax_rate\":null}],\"total\":51.15,\"balance\":51.15,\"url\":\"https://dundermifflin.invoiced.com/credit_notes/IZmXbVOPyvfD3GPBmyd6FwXY\",\"pdf_url\":\"https://dundermifflin.invoiced.com/credit_notes/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\"created_at\":1415229884,\"metadata\":{}}";

			CreditNote i1 = mapper.readValue(jsonInString, CreditNote.class);

			assertTrue("Id is incorrect", i1.id == 2048L);
			assertTrue("Customer is incorrect", i1.customer == 15444L);
			assertTrue("Name is incorrect", i1.name == null);
			assertTrue("Object is credit_note", i1.name == null);
			assertTrue("Invoice is incorrect", i1.invoice == 46225L);
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