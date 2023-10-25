package com.mjc.school.config.security;

import com.mjc.school.exception.CustomAccessDeniedHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //private final UserDetailsService userDetailsService;
    private final CustomAccessDeniedHandlerImpl accessDeniedHandler;
//    private final CustomTokenFilter tokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                System.out.println("handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)");
                            }
                        })
                )
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        System.out.println("handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)");
//                    }
//                })
//                .and()
                .authorizeHttpRequests(authorizeHttpRequests -> {
                            authorizeHttpRequests.antMatchers("/auth/**").permitAll();
                            authorizeHttpRequests.antMatchers(HttpMethod.GET, "").permitAll();
                            authorizeHttpRequests.anyRequest().authenticated();
                        }
                )
//                .authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
//                .antMatchers(HttpMethod.GET, "").permitAll()
//                .anyRequest().authenticated()
//                .and()
                .build();
//        http
//                .cors().and().csrf().disable()
//                .exceptionHandling()
////                .authenticationEntryPoint(authenticationEntryPoint)   It doesn't work with oAuth2
//                .accessDeniedHandler(accessDeniedHandler)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/certificate/**").permitAll()
//                .antMatchers( "/login/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();
//        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.build();
//                http.cors().and().csrf().disable()
//                .exceptionHandling()
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//
//                    }
//                })
//                .and()
//                .authorizeRequests()
//                .antMatchers().permitAll()
//                .antMatchers(HttpMethod.GET, "/certificate/**").permitAll()
//                .antMatchers( "/login/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login();

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