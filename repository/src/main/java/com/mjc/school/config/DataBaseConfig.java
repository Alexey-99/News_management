package com.mjc.school.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@PropertySource("classpath:application.properties")
//@EnableTransactionManagement
public class DataBaseConfig {
//    @Value("${db.url}")
//    private String url;
//
//    @Value("${db.username}")
//    private String userName;
//
//    @Value("${db.password}")
//    private String password;
//
//    @Value("${db.driver}")
//    private String driverClassName;
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(url);
//        config.setUsername(userName);
//        config.setPassword(password);
//        config.setDriverClassName(driverClassName);
//        return new HikariDataSource(config);
//    }
//
//    @Bean(name = "namedJdbcTemplate")
//    public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource) {
//        return new NamedParameterJdbcTemplate(dataSource);
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}