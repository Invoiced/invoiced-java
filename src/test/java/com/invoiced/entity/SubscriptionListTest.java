package com.invoiced.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SubscriptionListTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule();

  @Test
  public void testList() {

    // references connection_rr_45.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {
      EntityList<Subscription> subscriptions = conn.newSubscription().listAll();

      assertTrue("Subscription 1 id is incorrect", subscriptions.get(0).id == 595);

      assertTrue("Subscription 1 id is incorrect", subscriptions.get(1).id == 596);

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
          "[{\n    \"id\": 595,\n    \"customer\": 15444,\n    \"plan\": \"starter\",\n    \"cycles\": null,\n    \"quantity\": 1,\n    \"start_date\": 1420391704,\n    \"period_start\": 1446657304,\n    \"period_end\": 1449249304,\n    \"cancel_at_period_end\": false,\n    \"canceled_at\": null,\n    \"status\": \"active\",\n    \"addons\": [\n        {\n            \"id\": 3,\n            \"catalog_item\": \"ipad-license\",\n            \"quantity\": 11,\n            \"created_at\": 1420391704\n        }\n    ],\n    \"discounts\": [],\n    \"taxes\": [],\n    \"url\": \"https://dundermifflin.invoiced.com/subscriptions/o2mAd2wWVfYy16XZto7xHwXX\",\n    \"created_at\": 1420391704,\n    \"metadata\": {}\n},{\n    \"id\": 596,\n    \"customer\": 15444,\n    \"plan\": \"starter\",\n    \"cycles\": null,\n    \"quantity\": 1,\n    \"start_date\": 1420391704,\n    \"period_start\": 1446657304,\n    \"period_end\": 1449249304,\n    \"cancel_at_period_end\": false,\n    \"canceled_at\": null,\n    \"status\": \"active\",\n    \"addons\": [\n        {\n            \"id\": 3,\n            \"catalog_item\": \"ipad-license\",\n            \"quantity\": 11,\n            \"created_at\": 1420391704\n        }\n    ],\n    \"discounts\": [],\n    \"taxes\": [],\n    \"url\": \"https://dundermifflin.invoiced.com/subscriptions/o2mAd2wWVfYy16XZto7xHwXX\",\n    \"created_at\": 1420391704,\n    \"metadata\": {}\n}]";

      EntityList<Subscription> s1 =
          mapper.readValue(jsonInString, new TypeReference<EntityList<Subscription>>() {});

      assertTrue("Size is incorrect", s1.size() == 2);
      assertTrue("Id1 is incorrect", s1.get(0).id == 595);
      assertTrue("Id2 is incorrect", s1.get(1).id == 596);

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
