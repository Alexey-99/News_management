package com.mjc.school.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;

@Component
@WebFilter(urlPatterns = "api/v2/*")
public class PaginationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String size = request.getParameter("size");
        String page = request.getParameter("page");
        size = size != null && size.matches("^\\d+$") && Integer.parseInt(size) > 0 ? size : DEFAULT_SIZE;
        page = page != null && page.matches("^\\d+$") && Integer.parseInt(size) > 0 ? page : DEFAULT_NUMBER_PAGE;
        request.setAttribute("size", size);
        request.setAttribute("page", page);
        filterChain.doFilter(request, response);
    }
}