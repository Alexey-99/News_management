package com.mjc.school.config.security;

import com.mjc.school.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final UserService userService;



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.httpBasic().and()
//                .authorizeHttpRequests(authorizeHttpRequests -> {
//                            authorizeHttpRequests.antMatchers(HttpMethod.GET, "/api/v2/**").permitAll();
//                            authorizeHttpRequests.anyRequest().authenticated();
//                        }
//                ).exceptionHandling(exceptionHandling ->
//                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)
//                )
//                .build();
//    }
}