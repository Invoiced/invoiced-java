package com.invoiced.entity;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void testParentID() {
        Connection conn = new Connection("api_key", "http://localhost:8080");


        File file = conn.newFile();

        assertTrue("File hasList is incorrect", !file.hasList());
    }

    @Test
    public void testCreate() {

        // references files_create.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        File file = conn.newFile();
        file.url = "https://invoiced.com/img/logo-invoice.png";
        file.size = 6936L;
        file.name = "logo-invoice.png";
        file.type = "image/png";

        try {

            file.create();

            assertTrue("File Id is incorrect", file.id == 13);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testRetrieve() {

        // references files_retrieve.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            File file = conn.newFile().retrieve(13);
            assertTrue("File size in incorrect", file.size == 6936);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDelete() {

        // references files_retrieve.json
        // references files_delete.json

        Connection conn = new Connection("api_key", "http://localhost:8080");


        try {
            File file = conn.newFile().retrieve(13);
            file.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testJsonSerialization() {

        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            String jsonInString =
                    "{\n  \"id\": 13,\n  \"object\": \"file\",\n  \"name\": \"logo-invoice.png\",\n  \"size\": 6936,\n  \"type\": \"image/png\",\n  \"url\": \"https://invoiced.com/img/logo-invoice.png\",\n  \"created_at\": 1464625855\n}";

            File c1 = mapper.readValue(jsonInString, File.class);

            assertTrue("Id is incorrect", c1.id == 13);
            assertTrue("Object is incorrect", c1.object.equals("file"));
            assertTrue("Name is incorrect", c1.name.equals("logo-invoice.png"));

            assertTrue("Size is incorrect", c1.size == 6936);
            assertTrue("Type is incorrect", c1.type.equals("image/png"));
            assertTrue("Url is incorrect", c1.url.equals("https://invoiced.com/img/logo-invoice.png"));

            assertTrue("CreatedAt is incorrect", c1.createdAt == 1464625855L);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
            fail();
        } catch (JsonMappingException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
