package com.att.university;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Scanner;

@Configuration
@ComponentScan
@PropertySource("classpath:app.properties")
public class Config {
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource source) {
        return new JdbcTemplate(source);
    }

    @Bean
    public DataSource dataSource(@Value("${connection.url}") String url,
                                 @Value("${connection.username}") String username,
                                 @Value("${connection.password}") String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
