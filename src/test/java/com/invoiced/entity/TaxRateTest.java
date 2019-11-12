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

public class TaxRateTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_110.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

        TaxRate taxRate = conn.newTaxRate();
        taxRate.id = "vat";
        taxRate.name = "VAT";
        taxRate.value = 5L;

		try {
			taxRate.create();

			assertTrue("TaxRate id is incorrect", taxRate.id.equals("vat"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_111.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			TaxRate taxRate = conn.newTaxRate().retrieve("vat");

			assertTrue("TaxRate value is incorrect", taxRate.value == 5);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_111.json
		// references connection_rr_112.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			TaxRate taxRate = conn.newTaxRate().retrieve("vat");
			taxRate.name = "Updated";

			taxRate.save();

			assertTrue("TaxRate name is incorrect", taxRate.name.equals("Updated"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_111.json
		// references connection_rr_113.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			TaxRate taxRate = conn.newTaxRate().retrieve("vat");
			taxRate.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

    }
    
	@Test
	public void testList() {

		// references connection_rr_114.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			EntityList<TaxRate> taxRates = conn.newTaxRate().listAll();

			assertTrue("Id 1 is incorrect", taxRates.get(0).id.equals("vat"));

			assertTrue("Id 2 is incorrect", taxRates.get(1).id .equals("vat2"));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}