package com.invoiced.entity;

import org.junit.Test;
import static org.junit.Assert.*;



public class AbstractEntityTest {



	@Test public void testDiscount() {

		Discount discount = new Discount();
		discount.id = 3231;

		String discountString = discount.toString();

		assertTrue("Discount Id is incorrect", discountString.contains("3231"));


	}


	@Test public void testRate() {

		Rate rate = new Rate();
		rate.id = "3231";

		String rateString = rate.toString();

		assertTrue("Rate Id is incorrect", rateString.contains("3231"));


	}






}