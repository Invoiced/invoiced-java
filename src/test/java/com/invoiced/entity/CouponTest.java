package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CouponTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references coupons_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        Coupon coupon = conn.newCoupon();
        coupon.id = "S8L47J";
        coupon.name = "Non-profit Discount";
        coupon.value = 20D;

        try {
            coupon.create();

            assertTrue("Coupon id is incorrect", coupon.id.equals("S8L47J"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references coupons_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Coupon coupon = conn.newCoupon().retrieve("S8L47J");

            assertTrue("Coupon value is incorrect", coupon.value == 20);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSave() {

        // references coupons_retrieve.json
        // references coupons_edit.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {

            Coupon coupon = conn.newCoupon().retrieve("S8L47J");
            coupon.name = "Updated";

            coupon.save();

            assertTrue("Coupon name is incorrect", coupon.name.equals("Updated"));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {

        // references coupons_retrieve.json
        // references coupons_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            Coupon coupon = conn.newCoupon().retrieve("S8L47J");
            coupon.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testList() {

        // references coupons_list.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            EntityList<Coupon> coupons = conn.newCoupon().listAll();

            assertTrue("Id 1 is incorrect", coupons.get(0).id.equals("S8L47J"));

            assertTrue("Id 2 is incorrect", coupons.get(1).id.equals("S8L47J2"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
