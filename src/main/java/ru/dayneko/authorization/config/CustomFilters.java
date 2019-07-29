package ru.dayneko.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Кастомные фильтры для дальнейшего логгирования
 * запросов в бд
 *
 * @author dayneko_si
 * @since 29.05.2019
 */
@Configuration
public class CustomFilters {
    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter(){
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/**");

        return registrationBean;
    }
}

@Slf4j
@Order(1)
class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        log.info("Starting a request with URI : {}", req.getRequestURI());
        chain.doFilter(request, response);
        log.info("Finishing a request with URI : {}", req.getRequestURI());
    }
}
