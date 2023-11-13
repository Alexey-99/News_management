package com.mjc.school.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@ComponentScan("com.mjc.school")
@TestPropertySource(locations = "classpath:test_application.properties")
@SpringBootConfiguration
@Sql(scripts = "/schema.sql")
public class RepositoryConfig {
}