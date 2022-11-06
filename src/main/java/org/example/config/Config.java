package org.example.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ComponentScan(basePackages = "org.example")
public class Config {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://bhlk3chxtwoinrwxfxut-postgresql.services.clever-cloud.com:5432/bhlk3chxtwoinrwxfxut");
        dataSource.setUsername("u2f96wzuwjusx0ugkcho");
        dataSource.setPassword("lTadZj0mldsxKVmyJeUs");

        return dataSource;
    }




    @Bean
    public JdbcTemplate getTemplate() throws URISyntaxException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }
}
