package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class TaskTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testCreate() {

		// references connection_rr_11.json
		// references connection_rr_86.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
            Task task = cust.newTask();
            task.name = "Call customer";
            task.action = "phone";
            task.dueDate = 1571288400L;
            task.userId = 1976L;
            task.create();

			assertTrue("Task id is incorrect", task.id == 788);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_11.json
		// references connection_rr_87.json

		Connection conn = new Connection("", true);
        conn.testModeOn();

		try {
            Customer cust = conn.newCustomer().retrieve(11);
			Task task = cust.newTask().retrieve(788);

			assertTrue("Name is incorrect", task.name.equals("example"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_11.json
		// references connection_rr_88.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			Customer cust = conn.newCustomer().retrieve(11);
			Task task = cust.newTask().retrieve(788);

			task.name = "edited";

			task.save();

			assertTrue("Task name is incorrect", task.name.equals("edited"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

        // references connection_rr_11.json
		// references connection_rr_89.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {
			Customer cust = conn.newCustomer().retrieve(11);
			Task task = cust.newTask().retrieve(788);

			task.delete();

		} catch (Exception e) {
			fail(e.getMessage());
		}

    }
    
    @Test
	public void testProtectedMethods() {

        // references connection_rr_11.json

		Connection conn = new Connection("", true);
        conn.testModeOn();
		try {
            Customer cust = conn.newCustomer().retrieve(11);
            Task task = cust.newTask();
            task.id = 222;
            assertTrue("Task Entity id is wrong", task.getEntityId() == 222);
            assertTrue("Task id is wrong", task.getParentID() == -1);
			task.setParentID(-1231);
			assertTrue("Task id is wrong", task.getParentID() == -1);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testList() {

        // references connection_rr_11.json
		// references connection_rr_90.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		try {

			EntityList<Task> tasks = conn.newTask().listAll();

			assertTrue("Total count is incorrect", tasks.getTotalCount() == 2);
			assertTrue("Id1 is incorrect", tasks.get(0).id == 455L);
			assertTrue("Id2 is incorrect", tasks.get(1).id == 456L);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}