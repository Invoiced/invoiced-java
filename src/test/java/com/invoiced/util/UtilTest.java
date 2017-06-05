package com.invoiced.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class UtilTest {
	@Test
	public void testJsonEquals1() {
		String s1 = "{\"testing-library\": \"WireMock\"}";
		String s2 = "{\"testing-library\":\"WireMock\"}";

		boolean isEqual = false;

		try {
			isEqual = Util.jsonEqual(s1, s2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertTrue("Json string should equal", isEqual);

	}

	@Test
	public void testJsonEquals2() {
		String s1 = "{\"name\": \"invd\", \"address\": \"austin\"}";
		String s2 = "{\"address\": \"austin\"   ,   \"name\":    \"invd\"      }";

		boolean isEqual = false;

		try {
			isEqual = Util.jsonEqual(s1, s2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertTrue("Json string should equal", isEqual);

	}

	@Test
	public void testJsonNotEqual1() {
		String s1 = "{\"testing-library\": \"WireMock\"}";
		String s2 = "{\"testing-library\":\"WireMock1\"}";

		boolean isEqual = false;

		try {
			isEqual = Util.jsonEqual(s1, s2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertTrue("Json string should not equal", !isEqual);

	}

	@Test
	public void testParseLinks() {

		String s = "<https://api.invoiced.com/customers?page=3&per_page=10>; rel=\"self\", <https://api.invoiced.com/customers?page=1&per_page=10>; rel=\"first\",<https://api.invoiced.com/customers?page=2&per_page=10>; rel=\"previous\",<https://api.invoiced.com/customers?page=4&per_page=10>; rel=\"next\",<https://api.invoiced.com/customers?page=5&per_page=10>; rel=\"last\"";

		Util.parseLinks(s);

	}

}