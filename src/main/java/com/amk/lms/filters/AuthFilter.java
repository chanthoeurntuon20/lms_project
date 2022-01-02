package com.amk.lms.filters;

import com.amk.lms.Repositories.UserRepository;
import com.amk.lms.services.impl.UserDetailServiceImpl;
import com.amk.lms.utils.JwtUtility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter  extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        String aud = null;

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            jwtToken = authenticationHeader.substring(7);
            username = jwtUtility.getUsernameFromToken(jwtToken);
            aud = userRepository.findByEmail(username).getChannelCode();
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtility.validateToken(jwtToken, userDetails, aud)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
