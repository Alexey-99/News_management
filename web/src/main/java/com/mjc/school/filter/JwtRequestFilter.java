package com.mjc.school.filter;

import com.mjc.school.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
    private static final String AUTHORIZATION_HEADER_VALUE_START_WITH = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        String userName = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_VALUE_START_WITH)) {
            jwt = authHeader.substring(7);
            try {
                userName = jwtTokenUtil.getUserName(jwt);
            } catch (ExpiredJwtException ex) {
                log.log(DEBUG, "Token lifetime has expired. Message: ".concat(ex.getMessage()));
            } catch (SignatureException ex) {
                log.log(DEBUG, "The signature is not correct. Message: ".concat(ex.getMessage()));
            } catch (UnsupportedJwtException ex) {
                log.log(DEBUG, "Unsupported jwt. Message: ".concat(ex.getMessage()));
            } catch (MalformedJwtException ex) {
                log.log(DEBUG, "Malformed jwt. Message: ".concat(ex.getMessage()));
            } catch (Exception ex) {
                log.log(DEBUG, "Invalid token. Message: ".concat(ex.getMessage()));
            }
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,
                    null,
                    jwtTokenUtil.getRoles(jwt)
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}