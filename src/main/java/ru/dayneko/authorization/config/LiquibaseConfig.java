package ru.dayneko.authorization.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Настройки подключения к Liquibase
 *
 * @author dayneko_si
 * @since 24.04.2019
 */
@Configuration
public class LiquibaseConfig {
    private DataSource dataSource;

    public LiquibaseConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        val liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/liquibase/liquibase-changelog.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema("auth");
        liquibase.setDropFirst(false);
        liquibase.setLiquibaseSchema("auth");

        return liquibase;
    }
}
