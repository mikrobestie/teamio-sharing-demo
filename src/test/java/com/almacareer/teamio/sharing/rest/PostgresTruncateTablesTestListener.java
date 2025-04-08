package com.almacareer.teamio.sharing.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;

/**
 * Test listener that truncates all tables in the PostgreSQL database after each test method.
 * This is useful for ensuring a clean state between tests.
 * <p>
 * Note: This class assumes that the database is using the default schema 'public'.
 * </p>
 */
@Slf4j
public class PostgresTruncateTablesTestListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext testContext) {
        JdbcTemplate jdbcTemplate = testContext.getApplicationContext().getBean(JdbcTemplate.class);
        List<String> tableNames = jdbcTemplate.queryForList(
                "SELECT tablename FROM pg_tables WHERE schemaname = 'public'", String.class);

        jdbcTemplate.execute("SET session_replication_role = 'replica'"); // turn off FK constraints
        jdbcTemplate.execute("TRUNCATE TABLE " + String.join(", ", tableNames) + " RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("SET session_replication_role = 'origin'");
        LOGGER.info("Truncated {} tables", tableNames.size());
    }
}