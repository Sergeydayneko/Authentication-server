package ru.dayneko.authorization.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class LiquibaseConfig {
    private final DataSource dataSource;

    @Value("${liquibase.changelog}")
    private String changeLog;

    @Value("${liquibase.defaultSchema}")
    private String defaultSchema;

    @Value("${liquibase.liquibaseSchema}")
    private String liquibaseSchema;


    @Bean
    public SpringLiquibase liquibase() {
        val liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLog);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(defaultSchema);
        liquibase.setDropFirst(false);
        liquibase.setLiquibaseSchema(liquibaseSchema);

        return liquibase;
    }
}
