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

public class CouponTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_105.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

        Coupon coupon = conn.newCoupon();
        coupon.id = "S8L47J";
        coupon.name = "Non-profit Discount";
        coupon.value = 20L;

		try {
			coupon.create();

			assertTrue("Coupon id is incorrect", coupon.id.equals("S8L47J"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_106.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Coupon coupon = conn.newCoupon().retrieve("S8L47J");

			assertTrue("Coupon value is incorrect", coupon.value == 20);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_106.json
		// references connection_rr_107.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

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

		// references connection_rr_106.json
		// references connection_rr_108.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Coupon coupon = conn.newCoupon().retrieve("S8L47J");
			coupon.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

    }
    
	@Test
	public void testList() {

		// references connection_rr_109.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			EntityList<Coupon> coupons = conn.newCoupon().listAll();

			assertTrue("Id 1 is incorrect", coupons.get(0).id.equals("S8L47J"));

			assertTrue("Id 2 is incorrect", coupons.get(1).id .equals("S8L47J2"));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}