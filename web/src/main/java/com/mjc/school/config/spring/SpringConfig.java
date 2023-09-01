package com.mjc.school.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Spring config.
 */
@Configuration
@ComponentScan("com.mjc.school")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
}