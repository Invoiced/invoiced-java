package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.invoiced.exception.*;
import com.invoiced.util.Util;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ConnectionTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testGet() {

        // references customers_retrieve.json

        String jsonBody =
                "{\n  \"id\": 15444,\n  \"number\": \"CUST-0001\",\n  \"name\": \"Acme\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\": \"NET 30\",\n  \"payment_source\": null,\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": \"Sarah Fisher\",\n  \"address1\": \"342 Amber St\",\n  \"address2\": null,\n  \"city\": \"Hill Valley\",\n  \"state\": \"CA\",\n  \"postal_code\": \"94523\",\n  \"country\": \"US\",\n  \"tax_id\": \"893-934835\",\n  \"phone\": \"(820) 297-2983\",\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

        Connection conn = new Connection("api_key", "http://localhost:8080");

        try {
            String url = "/customers/15444";

            String tmp = conn.get(url, null);

            assertTrue("Response is incorrect", Util.jsonEqual(jsonBody, tmp));

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetFail() {

        // references customers_retrieve_not_found.json

        String jsonBody =
                "{\n    \"type\": \"invalid_request\",\n    \"message\": \"Customer was not found: 51123223\"\n}";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/customers/51123223";

            conn.get(url, null);

        } catch (ApiException e) {
            try {
                assertTrue("Response is incorrect", Util.jsonEqual(jsonBody, e.getMessage()));
            } catch (IOException e1) {
                fail(e1.getMessage());
            }
            return;
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {

        // references customers_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/" + "customers" + "/21123";

            conn.delete(url);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void testDeleteFail() {

        // references customers_delete_not_found.json

        Connection conn = new Connection("api_key", "http://localhost:8080");



        String url = "/" + "customers" + "/21121";

        try {
            conn.delete(url);
        } catch (ApiException e) {
            return;
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdate() {

        // references customers_edit.json

        String expectedJson =
                "{\n  \"id\": 15444,\n  \"number\": \"CUST-0001\",\n  \"name\": \"Acme\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\": \"NET 14\",\n  \"payment_source\": null,\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": \"Sarah Fisher\",\n  \"address1\": \"342 Amber St\",\n  \"address2\": null,\n  \"city\": \"Hill Valley\",\n  \"state\": \"CA\",\n  \"postal_code\": \"94523\",\n  \"country\": \"US\",\n  \"tax_id\": \"893-934835\",\n  \"phone\": \"(820) 297-2983\",\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

        String jsonBody =
                "{  \n  \"payment_terms\":\"NET 14\",\n  \"attention_to\":\"Sarah Fisher\",\n  \"address1\":\"342 Amber St\",\n  \"city\":\"Hill Valley\",\n  \"state\":\"CA\",\n  \"postal_code\":\"94523\",\n  \"tax_id\":\"893-934835\",\n  \"phone\":\"(820) 297-2983\" \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/" + "customers" + "/15444";

            String tmp = conn.patch(url, jsonBody);

            assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, tmp));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testMockUpdateFail() {
        // references customers_edit_not_found.json

        String expectedJson =
                "{\n    \"type\": \"invalid_request\",\n    \"message\": \"Customer was not found: 77777\"\n}";

        String jsonBody =
                "{  \n  \"payment_terms\":\"NET 14\",\n  \"attention_to\":\"Sarah Fisher\",\n  \"address1\":\"342 Amber St\",\n  \"city\":\"Hill Valley\",\n  \"state\":\"CA\",\n  \"postal_code\":\"94523\",\n  \"tax_id\":\"893-934835\",\n  \"phone\":\"(820) 297-2983\" \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/" + "customers" + "/77777";

            conn.patch(url, jsonBody);

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

        // references customers_create.json

        String expectedJson =
                "{\n  \"id\": 15444,\n  \"number\": \"CUST-0001\",\n  \"name\": \"Acme\",\n  \"email\": \"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\": \"NET 30\",\n  \"payment_source\": null,\n  \"taxes\": [],\n  \"type\": \"company\",\n  \"attention_to\": null,\n  \"address1\": null,\n  \"address2\": null,\n  \"city\": null,\n  \"state\": null,\n  \"postal_code\": null,\n  \"country\": \"US\",\n  \"tax_id\": null,\n  \"phone\": null,\n  \"notes\": null,\n  \"statement_pdf_url\": \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n  \"created_at\": 1415222128,\n  \"metadata\": {}\n}";

        String jsonBody =
                "{  \n  \"name\":\"Acme\",\n  \"email\":\"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\" \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/" + "customers";

            String tmp = conn.post(url, null, jsonBody);

            assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, tmp));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateFailInvalidRequest() {

        // references customers_create_invalid_request.json

        String expectedJson =
                "{\n    \"type\": \"invalid_request\",\n    \"message\": \"Name missing\",\n    \"param\": \"name\"\n}";

        String jsonBody =
                "{  \n \n  \"email\":\"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\",\n  \"invalid_request\":true \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/customers";

            conn.post(url, null, jsonBody);

        } catch (InvalidRequestException e) {
            try {
                assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, e.getMessage()));
            } catch (IOException e1) {
                fail(e1.getMessage());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateFailRateLimit() {

        // references customers_create_rate_limit.json

        String expectedJson =
                "{\n    \"type\": \"rate_limit_error\",\n    \"message\": \"You have reached your rate limit\"}";

        String jsonBody =
                "{  \n \n  \"email\":\"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\",\n  \"rate_limit\":true \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/customers";

            conn.post(url, null, jsonBody);

        } catch (RateLimitException e) {
            try {
                assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, e.getMessage()));
            } catch (IOException e1) {
                fail(e1.getMessage());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateFailAuthenticationError() {

        // references customers_create_authentication_error.json

        String expectedJson =
                "{\n    \"type\": \"authentication_error\",\n    \"message\": \"Invalid API key: XXXX\",\n    \"param\": \"name\"\n}";

        String jsonBody =
                "{  \n \n  \"email\":\"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\",\n  \"auth_error\":true \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/customers";

            conn.post(url, null, jsonBody);

        } catch (AuthException e) {
            try {
                assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, e.getMessage()));
            } catch (IOException e1) {
                fail(e1.getMessage());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateFailServerError() {

        // references customers_create_internal_server_error.json

        String expectedJson =
                "{\n    \"type\": \"server_error\",\n    \"message\": \"Internal Server Error\"\n}";

        String jsonBody =
                "{  \n \n  \"email\":\"billing@acmecorp.com\",\n  \"autopay\": false,\n  \"payment_terms\":\"NET 30\",\n  \"type\":\"company\",\n  \"server_error\":true \n }";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/customers";

            conn.post(url, null, jsonBody);

        } catch (ApiException e) {
            try {
                assertTrue("Response is incorrect", Util.jsonEqual(expectedJson, e.getMessage()));
            } catch (IOException e1) {
                fail(e1.getMessage());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelay() {

        // references customers_retrieve.json

        // String jsonBody = "{\n \"id\": 15444,\n \"number\": \"CUST-0001\",\n
        // \"name\": \"Acme\",\n \"email\": \"billing@acmecorp.com\",\n
        // \"autopay\": false,\n \"payment_terms\": \"NET 30\",\n
        // \"payment_source\": null,\n \"taxes\": [],\n \"type\": \"company\",\n
        // \"attention_to\": \"Sarah Fisher\",\n \"address1\": \"342 Amber
        // St\",\n \"address2\": null,\n \"city\": \"Hill Valley\",\n \"state\":
        // \"CA\",\n \"postal_code\": \"94523\",\n \"country\": \"US\",\n
        // \"tax_id\": \"893-934835\",\n \"phone\": \"(820) 297-2983\",\n
        // \"notes\": null,\n \"statement_pdf_url\":
        // \"https://dundermifflin.invoiced.com/statements/t3NmhUomra3g3ueSNnbtUgrr/pdf\",\n
        // \"created_at\": 1415222128,\n \"metadata\": {}\n}";

        Connection conn = new Connection("api_key", "http://localhost:8080");



        try {
            String url = "/" + "delayed";

            conn.get(url, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
