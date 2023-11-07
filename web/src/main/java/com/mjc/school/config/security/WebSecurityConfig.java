package com.mjc.school.config.security;

import com.mjc.school.filter.JwtRequestFilter;
import com.mjc.school.oauth.CustomSuccessHandler;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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

@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private static final String USER_ROLE_NAME = "USER";
    private static final String ADMIN_ROLE_NAME = "ADMIN";
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().and().cors().disable()
//                .formLogin().loginPage("/login").successHandler(customSuccessHandler)
//                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v2/author/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/comment/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/news/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v2/tag/*").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v2/auth/token").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v2/user/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/code").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v2/comment").hasAnyRole(USER_ROLE_NAME, ADMIN_ROLE_NAME)
                .antMatchers(HttpMethod.POST, "/api/v2/news").hasAnyRole(USER_ROLE_NAME, ADMIN_ROLE_NAME)
                .anyRequest().hasRole(ADMIN_ROLE_NAME)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .oauth2Login()
//                .loginPage("/login")
                .successHandler(customSuccessHandler)
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