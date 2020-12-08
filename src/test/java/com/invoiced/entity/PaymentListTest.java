package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PaymentListTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testList() {

        // references payments_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<Payment> payments = conn.newPayment().listAll();

            assertTrue("Payment 1 id is incorrect", payments.get(0).id == 20939);

            assertTrue("Payment 1 id is incorrect", payments.get(1).id == 20940);

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
                    "[{\n    \"id\": 20939,\n    \"customer\": 15460,\n    \"invoice\": 44648,\n    \"date\": 1410843600,\n    \"method\": \"check\",\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"notes\": null,\n    \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n    \"created_at\": 1415228628\n},{    \"id\": 20940,\n    \"customer\": 15460,\n    \"date\": 1410843600,\n    \"method\": \"check\",\n    \"currency\": \"usd\",\n    \"amount\": 800,\n    \"notes\": null,\n    \"pdf_url\": \"https://dundermifflin.invoiced.com/payments/IZmXbVOPyvfD3GPBmyd6FwXY/pdf\",\n    \"created_at\": 1415228628\n}]";

            EntityList<Payment> t1 =
                    mapper.readValue(jsonInString, new TypeReference<EntityList<Payment>>() {
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
