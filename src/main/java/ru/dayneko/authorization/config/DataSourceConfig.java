package ru.dayneko.authorization.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * Настройки подключения к базе данных
 *
 * @author dayneko_si
 * @since 24.04.2019
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5431/auth");
        dataSourceBuilder.username("*");
        dataSourceBuilder.password("*");
        return dataSourceBuilder.build();
    }
}
