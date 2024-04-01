package com.restaurant.Restaurant.security.Jwt;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletResponse;




@Component
@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

//    private String HEADER = "Authorization";
//    private String PREFIX = "Bearer ";
//    @Value("${jwt.secret}")
//	private String SECRET;



    
//    protected void doFilterInternal2(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            if (existJWTToken(request, response)) {
//                Claims claims = validateToken(request);
//                if (claims.get("authorities") != null) {
//                    setUpSpringAuthentication(claims);
//                } else {
//                    SecurityContextHolder.clearContext();
//                }
//            } else {
//                SecurityContextHolder.clearContext();
//            }
//            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//            return;
//        }
//    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean existJWTToken(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) {
        String authenticationHeader = request.getHeader("Authorization");
        return authenticationHeader != null && authenticationHeader.startsWith("Bearer ");
    }

    private Claims validateToken(jakarta.servlet.http.HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        return Jwts.parser().setSigningKey("yourSecretKey".getBytes()).parseClaimsJws(jwtToken).getBody();
    }


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        try {
        if (existJWTToken(request, response)) {
            Claims claims = validateToken(request);
            if (claims.get("authorities") != null) {
                setUpSpringAuthentication(claims);
            } else {
                SecurityContextHolder.clearContext();
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        return;
    }
    }
}
