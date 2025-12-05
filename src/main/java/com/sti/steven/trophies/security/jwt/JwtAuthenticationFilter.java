package com.sti.steven.trophies.security.jwt;

import com.sti.steven.trophies.service.UserDetailsService;
import io.micrometer.common.lang. NonNull;
import jakarta.servlet.FilterChain ;
import jakarta.servlet.ServletException ;
import jakarta.servlet.http.Cookie ;
import jakarta.servlet.http.HttpServletRequest ;
import jakarta.servlet.http.HttpServletResponse ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;
import org.springframework.beans.factory.annotation. Autowired;
import org.springframework.http.HttpHeaders ;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken ;
import org.springframework.security.core.context.SecurityContextHolder ;
import org.springframework.security.core.userdetails.UserDetails ;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource ;
import org.springframework.stereotype. Component;
import org.springframework.web.filter.OncePerRequestFilter ;
import java.io.IOException ;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil ;
    private final UserDetailsService customUserDetailsService ;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        logger.debug("---- JwtAuthenticationFilter START ----");

        try {
            String token = extractJwtFromCookie(request);
            if (token == null) token = extractJwtFromRequest(request);
            if (token != null && jwtUtil.validateJwtToken(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                logger.debug("Token: {}, valid: {}", token, jwtUtil.validateJwtToken(token));

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                    if (userDetails != null && userDetails.isEnabled()) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.debug("Authenticated user '{}'", username);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
        logger.debug("---- JwtAuthenticationFilter END ----");
    }

    private String extractJwtFromCookie(HttpServletRequest request) {
        if(request.getCookies() == null)
            return null;

        for(Cookie cookie : request.getCookies()) {
            if("authToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}
