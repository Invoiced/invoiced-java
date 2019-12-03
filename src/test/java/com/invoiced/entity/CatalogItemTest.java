package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CatalogItemTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule();

  @Test
  public void testCreate() {

    // references connection_rr_95.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    CatalogItem catalogItem = conn.newCatalogItem();
    catalogItem.id = "delivery";
    catalogItem.object = "failure_condition";
    catalogItem.name = "Delivery";
    catalogItem.type = "service";
    catalogItem.unitCost = 100L;

    try {
      catalogItem.create();

      assertTrue("CatalogItem id is incorrect", catalogItem.id.equals("delivery"));
      assertTrue("createdAt is not populated", catalogItem.createdAt == 1477327516);

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testRetrieve() {

    // references connection_rr_96.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {
      CatalogItem catalogItem = conn.newCatalogItem().retrieve("delivery");

      assertTrue("Catalog item type is incorrect", catalogItem.type.equals("service"));

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testSave() {

    // references connection_rr_97.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {

      CatalogItem catalogItem = conn.newCatalogItem().retrieve("delivery");
      catalogItem.name = "Updated";

      catalogItem.save();

      assertTrue("Catalog item name is incorrect", catalogItem.name.equals("Updated"));

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDelete() {

    // references connection_rr_98.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {
      CatalogItem catalogItem = conn.newCatalogItem().retrieve("delivery");
      catalogItem.delete();

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testList() {

    // references connection_rr_99.json

    Connection conn = new Connection("", true);
    conn.testModeOn();

    try {
      EntityList<CatalogItem> catalogItems = conn.newCatalogItem().listAll();

      assertTrue("Id 1 is incorrect", catalogItems.get(0).id.equals("delivery"));

      assertTrue("Id 2 is incorrect", catalogItems.get(1).id.equals("delivery2"));

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
