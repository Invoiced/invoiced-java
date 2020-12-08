package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TransactionListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testList() {

        // references connection_rr_48.json

        Connection conn = new Connection("", true);
        conn.testModeOn();

        try {
            EntityList<Transaction> transactions = conn.newTransaction().listAll();

            assertTrue("Transaction 1 id is incorrect", transactions.get(0).id == 20939);

            assertTrue("Transaction 1 id is incorrect", transactions.get(1).id == 20940);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testJsonSerialization() {

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonInString =
                    "[{\n    \"id\": 20939,\n    \"customer\": 15460,\n    \"invoice\": 44648,\n    \"date\": 1410843600,\n    \"type\": \"payment\",\n    \"method\": \"check\",\n    \"status\": \"succeeded\",\n    \"gateway\": null,\n    \"gateway_id\": null,\n    \"payment_source\": null,\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"fee\": 0,\n    \"notes\": null,\n    \"parent_transaction\": null,\n    \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n    \"created_at\": 1415228628,\n    \"metadata\": {}\n},{\n    \"id\": 20940,\n    \"customer\": 15460,\n    \"invoice\": 44648,\n    \"date\": 1410843600,\n    \"type\": \"payment\",\n    \"method\": \"check\",\n    \"status\": \"succeeded\",\n    \"gateway\": null,\n    \"gateway_id\": null,\n    \"payment_source\": null,\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"fee\": 0,\n    \"notes\": null,\n    \"parent_transaction\": null,\n    \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n    \"created_at\": 1415228628,\n    \"metadata\": {}\n}]";

            EntityList<Transaction> t1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<Transaction>>() {
                    });

            assertTrue("Size is incorrect", t1.size() == 2);
            assertTrue("Id1 is incorrect", t1.get(0).id == 20939);
            assertTrue("Id2 is incorrect", t1.get(1).id == 20940);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testJsonDeserialization() {
    }
}
