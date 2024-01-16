package com.mjc.school.util;

import com.mjc.school.exception.CustomAccessDeniedException;
import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mjc.school.model.user.User.UserRole.ROLE_ADMIN;
import static com.mjc.school.model.user.User.UserRole.ROLE_USER;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private static final String CLAIM_NAME_ROLES = "roles";
    @Value("${jwt.secret.access}")
    private String jwtAccessSecret;
    @Value("${jwt.secret.refresh}")
    private String jwtRefreshSecret;
    @Value("${jwt.life-time.access}")
    private Duration jwtAccessLifeTime;
    @Value("${jwt.life-time.refresh}")
    private Duration jwtRefreshLifeTime;

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put(CLAIM_NAME_ROLES, rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtAccessLifeTime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Date issuedDate = new Date();
//        Date expiredDate = new Date(issuedDate.getTime() + jwtRefreshLifeTime.toMillis());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .signWith(SignatureAlgorithm.HS256, jwtRefreshSecret)
                .compact();
    }

    public String getUserNameAccessToken(String token) {
        return getAllClaimsFromToken(token, jwtAccessSecret).getSubject();
    }

    public Date getExpirationAccessToken(String token) {
        return getAllClaimsFromToken(token, jwtAccessSecret).getExpiration();
    }

    public String getUserNameRefreshToken(String token) {
        return getAllClaimsFromToken(token, jwtRefreshSecret).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token, jwtAccessSecret)
                .get(CLAIM_NAME_ROLES, List.class);
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    public boolean isUser(ValidationJwtToken validationJwtToken) throws CustomAuthenticationException, CustomAccessDeniedException {
        if (validationJwtToken != null && validateAccessToken(validationJwtToken.getJwtToken())) {
            boolean result = !getRoles(validationJwtToken.getJwtToken())
                    .stream()
                    .filter(role -> role != null &&
                            (role.equalsIgnoreCase(ROLE_USER.name())
                                    || role.equalsIgnoreCase(ROLE_ADMIN.name())))
                    .toList()
                    .isEmpty();
            if (!result) {
                throw new CustomAccessDeniedException("exception.access_denied");
            }
        } else {
            throw new CustomAuthenticationException("exception.access_without_authorization");
        }
        return true;
    }

    public boolean isAdmin(ValidationJwtToken validationJwtToken) throws CustomAuthenticationException, CustomAccessDeniedException {
        if (validationJwtToken != null && validateAccessToken(validationJwtToken.getJwtToken())) {
            boolean result = !getRoles(validationJwtToken.getJwtToken())
                    .stream()
                    .filter(role -> role != null && role.equalsIgnoreCase(ROLE_ADMIN.name()))
                    .toList()
                    .isEmpty();
            if (!result) {
                throw new CustomAccessDeniedException("exception.access_denied");
            }
        } else {
            throw new CustomAuthenticationException("exception.access_without_authorization");
        }
        return true;
    }

    private boolean validateToken(String token, String secret) {
        if (token != null) {
            try {
                Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException expEx) {
                log.error("Token expired: {}", expEx.getMessage());
            } catch (UnsupportedJwtException unsEx) {
                log.error("Unsupported jwt: {}", unsEx.getMessage());
            } catch (MalformedJwtException mjEx) {
                log.error("Malformed jwt: {}", mjEx.getMessage());
            } catch (SignatureException sEx) {
                log.error("Invalid signature: {}", sEx.getMessage());
            } catch (Exception e) {
                log.error("invalid token: {}", e.getMessage());
            }
        }
        return false;
    }

    private Claims getAllClaimsFromToken(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}