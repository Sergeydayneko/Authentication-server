package ru.dayneko.authorization.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.dayneko.authorization.config.JwtConfig;
import ru.dayneko.authorization.config.WebSecurityConfig;
import ru.dayneko.authorization.providers.JwtAuthenticationProvider;
import ru.dayneko.authorization.providers.UserAuthenticationProvider;
import ru.dayneko.authorization.services.UserService;
import ru.dayneko.authorization.utils.jwt.TokenExtractor;
import ru.dayneko.authorization.utils.dto.UserDtoMapper;

import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
@Import(WebSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

//    @Autowired
    private MockMvc mvc;

//    @Autowired
//    private TestRestTemplate template;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    @Qualifier("successHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier("failureHandler")
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    @Qualifier("tokenExtractor")
    private TokenExtractor tokenExtractor;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("userDtoMapper")
    private UserDtoMapper userDtoMapper;

//    Необходимо при использовании SpringBootTest
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @TestConfiguration
    static class initTestConfig {
        @Bean
        JwtConfig initJwtConfig() {
            return mock(JwtConfig.class);
        }

        @Bean
        UserAuthenticationProvider initUserAuthenticationProvider() {
            return mock(UserAuthenticationProvider.class);
        }

        @Bean
        JwtAuthenticationProvider initJwtAuthenticationProvider() {
            return mock(JwtAuthenticationProvider.class);
        }

        @Bean("successHandler")
        AuthenticationSuccessHandler initSuccessHandler() {
            return mock(AuthenticationSuccessHandler.class);
        }

        @Bean("failureHandler")
        AuthenticationFailureHandler initFailureHandler() {
            return mock(AuthenticationFailureHandler.class);
        }

        @Bean
        ObjectMapper initObjectMapper() {
            return mock(ObjectMapper.class);
        }

        @Bean("tokenExtractor")
        TokenExtractor initTokenExtractor() {
            return mock(TokenExtractor.class);
        }

        @Bean("userService")
        UserService initUserService() {
            return mock(UserService.class);
        }

        @Bean("userDtoMapper")
        UserDtoMapper initUserDtoMapper() {
            return mock(UserDtoMapper.class);
        }

    }

    @Test
//    @WithMockUser(value = "sdayneko")
    public void testAuthorizationErrorWhenDontHaveAccessToken() throws Exception {
        mvc.perform(get("/ws-dsp/v1/security")
//                .with(user("sdayneko").password("123"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
//                .andExpect(content().string("Hello"));
    }

    @Test
    @WithMockUser(value = "sdayneko")
    public void testToken() throws Exception {
        mvc.perform(post("/ws-dsp/v1/login")
                .with(user("sdayneko").password("123")))
                .andDo(print());
    }
}
