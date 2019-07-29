package ru.dayneko.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.dayneko.authorization.providers.JwtAuthenticationProvider;
import ru.dayneko.authorization.providers.UserAuthenticationProvider;
import ru.dayneko.authorization.filters.AuthenticationFilterFactory;

import javax.servlet.http.HttpServletResponse;

import static ru.dayneko.authorization.model.filter.AuthenticationFilter.HttpRequest;
import static ru.dayneko.authorization.model.filter.AuthenticationFilter.UsernameAndPassword;

/**
 * Настройка Spring Security
 *
 * @author dayneko_si
 * @since 23.04.2019
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConfig jwtConfig;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AuthenticationFilterFactory filterFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${server.authentication.url.login-entry-point}")
    private String LOGIN_ENTRY_POINT;

    @Value("${server.authentication.url.api-entry-point}")
    private String API_ENTRY_POINT;

    @Value("${server.authentication.url.base-auth-url}")
    private String BASE_AUTH_URL;

    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

    private final String TOKEN_BASED_AUTH_ENTRY_POINT = API_ENTRY_POINT + BASE_AUTH_URL;

    WebSecurityConfig(
              JwtConfig jwtConfig
            , UserAuthenticationProvider userAuthenticationProvider
            , JwtAuthenticationProvider jwtAuthenticationProvider
            , AuthenticationFilterFactory filterFactory) {
        this.jwtConfig = jwtConfig;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.filterFactory = filterFactory;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterBefore(buildHttpRequestJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtUsernameAndPasswordFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, API_ENTRY_POINT + jwtConfig.getUri()).permitAll()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private AbstractAuthenticationProcessingFilter buildHttpRequestJwtAuthenticationFilter() {
        return filterFactory.getAuthenticationFilter(HttpRequest).setAuthManager(this.authenticationManager);
    }

    private AbstractAuthenticationProcessingFilter buildJwtUsernameAndPasswordFilter() {
        return filterFactory.getAuthenticationFilter(UsernameAndPassword).setAuthManager(this.authenticationManager);
    }
}
