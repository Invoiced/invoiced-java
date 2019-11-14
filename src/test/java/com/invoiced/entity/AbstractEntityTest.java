package com.invoiced.entity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AbstractEntityTest {

	@Test
	public void testDiscount() {

		Discount discount = new Discount();
		discount.id = 3231;

		String discountString = discount.toString();

		assertTrue("Discount Id is incorrect", discountString.contains("3231"));

	}

}