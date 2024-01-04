package com.mjc.school.config.security;

import com.mjc.school.exception.handler.CustomAccessDeniedHandlerImpl;
import com.mjc.school.exception.handler.CustomAuthenticationEntryPointImpl;
import com.mjc.school.filter.JwtRequestFilter;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAuthenticationEntryPointImpl customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandlerImpl customAccessDeniedHandler;
    private static final String ADMIN_ROLE_NAME = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(POST, "/api/v2/comment").authenticated()
                .antMatchers(POST, "/api/v2/news").authenticated()
                .antMatchers(POST, "/api/v2/tag").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(POST, "/api/v2/author").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(PUT, "/api/v2/tag/to-news").authenticated()
                .antMatchers(PUT, "/api/v2/tag/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(PUT, "/api/v2/news/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(PUT, "api/v2/comment/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(PUT, "api/v2/author/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(PATCH, "/api/v2/user/role").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "/api/v2/tag/from-news").authenticated()
                .antMatchers(DELETE, "/api/v2/tag/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "/api/v2/tag/all-news/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "/api/v2/news/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "/api/v2/news/author/{authorId}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "/api/v2/news/all-tags/{newsId}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "api/v2/comment/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "api/v2/comment/news/{newsId}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(DELETE, "/api/v2/author/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(GET, "/api/v2/user/all").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(GET, "/api/v2/user/{id}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(GET, "/api/v2/user/login/{login}").hasRole(ADMIN_ROLE_NAME)
                .antMatchers(GET, "/api/v2/user/role/{role}").hasRole(ADMIN_ROLE_NAME)
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // SessionCreationPolicy.STATELESS doesn't work with oAuth2
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)// It doesn't work with oAuth2
                .accessDeniedHandler(customAccessDeniedHandler)
//                .and()
//                .oauth2Login()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}