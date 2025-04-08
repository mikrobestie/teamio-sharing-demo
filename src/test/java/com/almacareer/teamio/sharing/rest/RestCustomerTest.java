package com.almacareer.teamio.sharing.rest;

import com.almacareer.teamio.sharing.jpa.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for the customer REST API controller.
 * Showcases various ways of asserting the response content.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestExecutionListeners(listeners = {
        DbUnitTestExecutionListener.class,
        PostgresTruncateTablesTestListener.class
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@Import(RestCustomerTest.PostgresContainerConfiguration.class)
@DatabaseSetup("/dbunit/dataset.xml")
class RestCustomerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    /**
     * Just check PostgreSQL version.
     */
    @Test
    void testVerifyDatabaseVersion() {
        Object result = entityManager.createNativeQuery("SELECT version()").getSingleResult();
        assertEquals("PostgreSQL 11.22 on aarch64-unknown-linux-musl, compiled by gcc (Alpine 13.2.1_git20231014) 13.2.1 20231014, 64-bit", result);
    }

    /**
     * Test search with returning JSON string and using classic `assertEquals`.
     */
    @Test
    void testSearch_assertEquals() throws Exception {
        String json = mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        assertEquals("{\"items\":[{\"id\":3,\"firstName\":\"Tom\",\"lastName\":\"Smith\"},{\"id\":4,\"firstName\":\"Jerry\",\"lastName\":\"Smith\"}],\"page\":1,\"size\":2,\"totalPages\":20,\"totalItems\":40}", json);
    }

    /**
     * Test search with returning JSON, converting it to object and using `assertEquals`.
     */
    @Test
    void testSearch_objectMapper() throws Exception {
        String json = mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        CustomerPage page = objectMapper.readValue(json, CustomerPage.class);

        assertEquals(1, page.getPage());
        assertEquals(2, page.getSize());
        assertEquals(20, page.getTotalPages());
        assertEquals(40, page.getTotalItems());
        assertEquals(3, page.getItems().getFirst().getId());
        assertEquals("Tom", page.getItems().getFirst().getFirstName());
        assertEquals("Smith", page.getItems().getFirst().getLastName());
        assertEquals(4, page.getItems().getLast().getId());
        assertEquals("Jerry", page.getItems().getLast().getFirstName());
        assertEquals("Smith", page.getItems().getLast().getLastName());
    }

    /**
     * Test search with check against string using `content().string()`.
     */
    @Test
    void testSearch_exactString() throws Exception {
        mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"items\":[{\"id\":3,\"firstName\":\"Tom\",\"lastName\":\"Smith\"},{\"id\":4,\"firstName\":\"Jerry\",\"lastName\":\"Smith\"}],\"page\":1,\"size\":2,\"totalPages\":20,\"totalItems\":40}"));
    }

    /**
     * Test search with check against string, multiline (better readability).
     */
    @Test
    void testSearch_exactMultilineString() throws Exception {
        mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //language=JSON
                .andExpect(content().string("""
                        {"items":[{"id":3,"firstName":"Tom","lastName":"Smith"},{"id":4,"firstName":"Jerry","lastName":"Smith"}],"page":1,"size":2,"totalPages":20,"totalItems":40}"""));
    }

    /**
     * Test search with standard jsonPath(..) asserts.
     */
    @Test
    void testSearch_jsonPath() throws Exception {
        mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalPages").value(20))
                .andExpect(jsonPath("$.totalItems").value(40))
                .andExpect(jsonPath("$.items[0].id").value(3))
                .andExpect(jsonPath("$.items[0].firstName").value("Tom"))
                .andExpect(jsonPath("$.items[0].lastName").value("Smith"))
                .andExpect(jsonPath("$.items[1].id").value(4))
                .andExpect(jsonPath("$.items[1].firstName").value("Jerry"))
                .andExpect(jsonPath("$.items[1].lastName").value("Smith"));
    }

    /**
     * Test search with `content().json()` method.
     */
    @Test
    void testSearch_jsonString() throws Exception {

        mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "items": [
                                {
                                    "id": 3
                                },
                                {
                                    "id": 4
                                }
                            ],
                            "page": 1
                        }
                        """));
    }

    /**
     * Test search with `content().json("...", true)` method.
     */
    @Test
    void testSearch_jsonExactString() throws Exception {

        mvc.perform(get("/api/customer?page=1&size=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "items": [
                            {
                              "firstName": "Tom",
                              "lastName": "Smith"
                            },
                            {
                              "id": 4,
                              "firstName": "Jerry",
                              "lastName": "Smith"
                            }
                          ],
                          "page": 1,
                          "size": 2,
                          "totalPages": 20,
                          "totalItems": 40
                        }""", true));
    }

    @Data
    static class CustomerPage {
        private List<Customer> items;
        private int page;
        private int size;
        private int totalPages;
        private int totalItems;
    }

    public static class PostgresContainerConfiguration {

        static {
            // Optional - ensure Docker is running
            DockerClientFactory.instance().client();
        }

        @Bean
        @ServiceConnection
        public PostgreSQLContainer<?> postgresContainer() {
            return new PostgreSQLContainer<>("postgres:11.22-alpine")
                    .withTmpFs(Map.of("/var/lib/postgresql/data", "rw")); // Improve performance
        }
    }
}