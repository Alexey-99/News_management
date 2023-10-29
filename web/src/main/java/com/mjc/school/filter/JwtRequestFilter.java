package com.mjc.school.filter;

import com.mjc.school.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.Level.DEBUG;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String START_WITH_AUTHORIZATION_HEADER_VALUE = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        String userName = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith(START_WITH_AUTHORIZATION_HEADER_VALUE)) {
            jwt = authHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUserName(jwt);
            } catch (ExpiredJwtException ex) {
                log.log(DEBUG, "Время жизни токена вышло");
            } catch (SignatureException ex) {
                log.log(DEBUG, "Подпись не корректна");
            }
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName,
                    null,
                    jwtTokenUtil.getRoles(jwt)
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}