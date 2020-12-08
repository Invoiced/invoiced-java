package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TaxRateTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references tax_rates_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        // references tax_rates_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            TaxRate taxRate = conn.newTaxRate().retrieve("vat");

            assertTrue("TaxRate value is incorrect", taxRate.value == 5);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references tax_rates_retrieve.json
        // references tax_rates_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        // references tax_rates_retrieve.json
        // references tax_rates_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            TaxRate taxRate = conn.newTaxRate().retrieve("vat");
            taxRate.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testList() {

        // references tax_rates_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<TaxRate> taxRates = conn.newTaxRate().listAll();

            assertTrue("Id 1 is incorrect", taxRates.get(0).id.equals("vat"));

            assertTrue("Id 2 is incorrect", taxRates.get(1).id.equals("vat2"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
