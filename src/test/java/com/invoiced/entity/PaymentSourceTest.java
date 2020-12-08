package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.invoiced.exception.EntityException;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PaymentSourceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreateDeleteAccountSource() throws EntityException {

        // references customers_retrieve_2.json
        // references customers_create_bank_account.json
        // references customers_delete_bank_account.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer().retrieve(11);

        SourceRequest sourceRequest = new SourceRequest();
        sourceRequest.method = "ach";

        PaymentSource source = cust.createPaymentSource(sourceRequest);

        assertTrue("Source type is not bank account", BankAccount.class.isInstance(source));

        source.delete();
    }

    @Test
    public void testCreateDeleteCardSource() throws EntityException {

        // references customers_retrieve_2.json
        // references customers_create_card.json
        // references customers_delete_card.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer().retrieve(11);

        SourceRequest sourceRequest = new SourceRequest();
        sourceRequest.method = "credit_card";

        PaymentSource source = cust.createPaymentSource(sourceRequest);

        assertTrue("Source type is not card", Card.class.isInstance(source));

        source.delete();
    }

    @Test
    public void testListAllSources() throws EntityException {

        // references customers_retrieve_2.json
        // references customers_list_payment_sources.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Customer cust = conn.newCustomer().retrieve(11);

        EntityList<PaymentSource> sources = cust.listPaymentSources();

        assertTrue("Total count is incorrect", sources.getTotalCount() == 2);
        assertTrue("Id1 is incorrect", sources.get(0).id == 101L);
        assertTrue("Source type is not card", Card.class.isInstance(sources.get(0)));
        assertTrue("Id2 is incorrect", sources.get(1).id == 102L);
        assertTrue("Source type is not bank account", BankAccount.class.isInstance(sources.get(1)));
    }
}
