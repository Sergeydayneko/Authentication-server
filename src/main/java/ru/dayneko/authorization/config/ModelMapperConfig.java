package ru.dayneko.authorization.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Настройка мэппера для конвертации
 * Entity в DTO
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
