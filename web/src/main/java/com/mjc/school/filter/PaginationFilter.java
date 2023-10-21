package com.mjc.school.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static com.mjc.school.service.pagination.PaginationService.DEFAULT_NUMBER_PAGE;
import static com.mjc.school.service.pagination.PaginationService.DEFAULT_SIZE;

@WebFilter(urlPatterns = {
        "/api/v2/news/*",
        "/api/v2/author/*",
        "/api/v2/comment/*",
        "/api/v2/tag/*"})
public class PaginationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        String size = servletRequest.getParameter("size");
        String page = servletRequest.getParameter("page");
        size = size != null && size.matches("^\\d+$") && Integer.parseInt(size) > 0 ? size : DEFAULT_SIZE;
        page = page != null && page.matches("^\\d+$") && Integer.parseInt(size) > 0 ? page : DEFAULT_NUMBER_PAGE;
        servletRequest.setAttribute("size", size);
        servletRequest.setAttribute("page", page);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}