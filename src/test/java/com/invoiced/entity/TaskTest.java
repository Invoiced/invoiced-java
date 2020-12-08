package com.invoiced.entity;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TaskTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testCreate() {

        // references connection_rr_11.json
        // references connection_rr_86.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        Connection conn = new Connection("api_key", "http://localhost:8080");


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

        Connection conn = new Connection("api_key", "http://localhost:8080");

        try {
            Customer cust = conn.newCustomer().retrieve(11);
            Task task = cust.newTask();
            task.id = 222L;
            assertTrue("Task Entity id is wrong", task.getEntityId().equals("222"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testList() {

        // references connection_rr_11.json
        // references connection_rr_90.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


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
