package ru.dayneko.authorization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger {
    @Value("${server.authentication.url.jwt-request-type}")
    private String JWT_REQUEST_TYPE;

    @Bean
    public Docket api() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new ApiKey(HttpHeaders.AUTHORIZATION, WebSecurityConfig.JWT_TOKEN_HEADER_PARAM, JWT_REQUEST_TYPE));

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.dayneko.authorization.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(schemeList)
                .useDefaultResponseMessages(false);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "AUTH SYSTEM API",
                "Authorization module",
                "2.9.2",
                "http://www.no-address.ru/",
                new Contact("DAYNEKO", "http://www.no-address.ru/", "sergeydayneko@mail.ru"),
                "sdayneko",
                "http://www.no-address.ru/", new ArrayList<>()
        );
    }
}
