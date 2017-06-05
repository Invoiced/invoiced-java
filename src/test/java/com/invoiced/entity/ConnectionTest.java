package com.invoiced.entity;

import org.junit.Test;
import static org.junit.Assert.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.invoiced.exception.*;
import java.io.IOException;
import org.junit.rules.ExpectedException;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import com.invoiced.util.Util;

public class ConnectionTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testGet() {

		// references connection_rr_1.json

		String jsonBody = "{\n  \"id\": 15444,\n  \"number\": \"CUST-0001\",\n  \"name\": \"Acme\",\n  \"email\": \"billing@acmecorp.com\",\n  \"collection_mode\": \"manual\",\n  \"payment_terms\": \"NET 30\",\n  \"payment_source\": null,\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": \"Sarah Fisher\",\n  \"address1\": \"342 Amber St\",\n  \"address2\": null,\n  \"city\": \"Hill Valley\",\n  \"state\": \"CA\",\n  \"postal_code\": \"94523\",\n  \"country\": \"US\",\n  \"tax_id\": \"893-934835\",\n  \"phone\": \"(820) 297-2983\",\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "customers/15444";

			String tmp = conn.get(url, null);

			assertTrue("Response is incorrect", Util.jsonEqual(jsonBody, tmp));

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());

		}

	}

	@Test
	public void testGetFail() {

		// references connection_rr_2.json

		String jsonBody = "{\n    \"type\": \"invalid_request\",\n    \"message\": \"Customer was not found: 51123223\"\n}";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/customers/51123223";

			String tmp = conn.get(url, null);

		} catch (ApiException e) {
			try {
				assertTrue("Response is incorrect", Util.jsonEqual(jsonBody, e.getMessage()));
			} catch (IOException e1) {
				fail(e1.getMessage());
			}
			return;
		} catch (AuthException e) {
			fail(e.getMessage());
		} catch (ConnException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_3.json

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "customers" + "/21123";

			conn.delete(url);

		} catch (Exception e) {
			fail(e.getMessage());

		}

	}

	public void testDeleteFail() {

		// references connection_rr_4.json

		Connection conn = new Connection("", true);

		conn.testModeOn();

		String url = conn.baseUrl() + "/" + "customers" + "/21121";

		try {
			conn.delete(url);
		} catch (ApiException e) {
			return;
		} catch (AuthException e) {
			fail(e.getMessage());
		} catch (ConnException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testUpdate() {

		// references connection_rr_5.json

		String expectedJson = "{\n  \"id\": 15444,\n  \"number\": \"CUST-0001\",\n  \"name\": \"Acme\",\n  \"email\": \"billing@acmecorp.com\",\n  \"collection_mode\": \"manual\",\n  \"payment_terms\": \"NET 14\",\n  \"payment_source\": null,\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": \"Sarah Fisher\",\n  \"address1\": \"342 Amber St\",\n  \"address2\": null,\n  \"city\": \"Hill Valley\",\n  \"state\": \"CA\",\n  \"postal_code\": \"94523\",\n  \"country\": \"US\",\n  \"tax_id\": \"893-934835\",\n  \"phone\": \"(820) 297-2983\",\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

		String jsonBody = "{  \n  \"payment_terms\":\"NET 14\",\n  \"attention_to\":\"Sarah Fisher\",\n  \"address1\":\"342 Amber St\",\n  \"city\":\"Hill Valley\",\n  \"state\":\"CA\",\n  \"postal_code\":\"94523\",\n  \"tax_id\":\"893-934835\",\n  \"phone\":\"(820) 297-2983\" \n }";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "customers" + "/15444";

			String tmp = conn.patch(url, jsonBody);

			assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, tmp));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testMockUpdateFail() {
		// references connection_rr_5.json

		String expectedJson = "{\n    \"type\": \"invalid_request\",\n    \"message\": \"Customer was not found: 77777\"\n}";

		String jsonBody = "{  \n  \"payment_terms\":\"NET 14\",\n  \"attention_to\":\"Sarah Fisher\",\n  \"address1\":\"342 Amber St\",\n  \"city\":\"Hill Valley\",\n  \"state\":\"CA\",\n  \"postal_code\":\"94523\",\n  \"tax_id\":\"893-934835\",\n  \"phone\":\"(820) 297-2983\" \n }";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "customers" + "/77777";

			String tmp = conn.patch(url, jsonBody);

		} catch (ApiException e) {
			try {
				assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, e.getMessage()));
			} catch (IOException e1) {
				fail(e1.getMessage());
			}
		} catch (AuthException e) {
			fail(e.getMessage());
		} catch (ConnException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testCreate() {

		// references connection_rr_7.json

		String expectedJson = "{\n  \"id\": 15444,\n  \"number\": \"CUST-0001\",\n  \"name\": \"Acme\",\n  \"email\": \"billing@acmecorp.com\",\n  \"collection_mode\": \"manual\",\n  \"payment_terms\": \"NET 30\",\n  \"payment_source\": null,\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": null,\n  \"address1\": null,\n  \"address2\": null,\n  \"city\": null,\n  \"state\": null,\n  \"postal_code\": null,\n  \"country\": \"US\",\n  \"tax_id\": null,\n  \"phone\": null,\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

		String jsonBody = "{  \n  \"name\":\"Acme\",\n  \"email\":\"billing@acmecorp.com\",\n  \"collection_mode\":\"manual\",\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\" \n }";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "customers";

			String tmp = conn.post(url, null, jsonBody);

			assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, tmp));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testCreateFail() {

		String expectedJson = "{\n    \"type\": \"invalid_request\",\n    \"message\": \"Name missing\",\n    \"param\": \"name\"\n}";

		String jsonBody = "{  \n \n  \"email\":\"billing@acmecorp.com\",\n  \"collection_mode\":\"manual\",\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\" \n }";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "customers";

			String tmp = conn.post(url, null, jsonBody);

		} catch (ApiException e) {
			try {
				assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, e.getMessage()));
			} catch (IOException e1) {
				fail(e1.getMessage());
			}
		} catch (AuthException e) {
			fail(e.getMessage());
		} catch (ConnException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelay() {

		// references connection_rr_1.json

		// String jsonBody = "{\n \"id\": 15444,\n \"number\": \"CUST-0001\",\n
		// \"name\": \"Acme\",\n \"email\": \"billing@acmecorp.com\",\n
		// \"collection_mode\": \"manual\",\n \"payment_terms\": \"NET 30\",\n
		// \"payment_source\": null,\n \"taxes\": [],\n \"type\": \"company\",\n
		// \"attention_to\": \"Sarah Fisher\",\n \"address1\": \"342 Amber
		// St\",\n \"address2\": null,\n \"city\": \"Hill Valley\",\n \"state\":
		// \"CA\",\n \"postal_code\": \"94523\",\n \"country\": \"US\",\n
		// \"tax_id\": \"893-934835\",\n \"phone\": \"(820) 297-2983\",\n
		// \"notes\": null,\n \"statement_pdf_url\":
		// \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n
		// \"created_at\": 1415222128,\n \"metadata\": {}\n}";

		Connection conn = new Connection("", true);

		conn.testModeOn();

		try {
			String url = conn.baseUrl() + "/" + "delayed";

			String tmp = conn.get(url, null);

			// assertTrue("Response is incorrect", Util.jsonEqual(jsonBody,
			// tmp));

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());

		}

	}

}