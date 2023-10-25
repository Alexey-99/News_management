package com.mjc.school.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic().and()
//                .exceptionHandling(exceptionHandling ->
//                        exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {
//                            @Override
//                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                                System.out.println("handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)");
//                            }
//                        })
//                )
                .authorizeHttpRequests(authorizeHttpRequests -> {
                            authorizeHttpRequests.antMatchers(HttpMethod.GET, "/api/v2/**").permitAll();
                            authorizeHttpRequests.anyRequest().authenticated();
                        }
                )
                .build();

//                .anonymous(AbstractHttpConfigurer::disable)         // AnonymousAuthenticationFilter
//                .csrf(AbstractHttpConfigurer::disable)              // CsrfFilter
//                .sessionManagement(AbstractHttpConfigurer::disable) // DisableEncodeUrlFilter, SessionManagementFilter
//                .exceptionHandling(AbstractHttpConfigurer::disable) // ExceptionTranslationFilter
//                .headers(AbstractHttpConfigurer::disable)           // HeaderWriterFilter
//                .logout(AbstractHttpConfigurer::disable)            // LogoutFilter
//                .requestCache(AbstractHttpConfigurer::disable)      // RequestCacheAwareFilter
//                .servletApi(AbstractHttpConfigurer::disable)        // SecurityContextHolderAwareRequestFilter
//                .securityContext(AbstractHttpConfigurer::disable)   // SecurityContextPersistenceFilter
//                .build();
    }
}