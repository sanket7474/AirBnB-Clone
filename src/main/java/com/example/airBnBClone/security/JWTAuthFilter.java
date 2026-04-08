package com.example.airBnBClone.security;

import com.example.airBnBClone.dto.UserDTO;
import com.example.airBnBClone.entities.User;
import com.example.airBnBClone.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
// Configuration annotation is used to indicate that this class is a
// configuration class that may contain bean definitions and other configuration settings.
//
// why configuration? Because we want to register this filter in the Spring Security filter chain,
// and using @Configuration allows us to do that easily by defining a bean for this filter in the security configuration class.
@Configuration
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        // Extract JWT token from the Authorization header
        // Why in authorization header?
        // Because it's a standard practice to send the token in the Authorization header as a Bearer token.
        // This allows the server to easily identify and extract the token for authentication and authorization purposes.
        //
        // can it be sent in custom header? Yes, but it's not a standard practice and may
        // require additional configuration on the server to recognize and extract the token from the custom header.
        final String requestTokenHeader = request.getHeader("Authorization");

        // Check if the token is present and starts with "Bearer "
        // If not, continue with the filter chain without setting authentication
        // This allows the request to proceed without authentication,
        // which may be necessary for certain endpoints that do not require authentication.
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenHeader.substring(7);
        Long userId = jwtService.getUserIdFromToken(token);


        // If the userId is not null and there is no authentication set in the SecurityContext,
        // we proceed to authenticate the user

        // This check is important to prevent re-authentication of the user if they are already authenticated in the current security context.
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.getUserById(userId);
            // We create a UsernamePasswordAuthenticationToken with the user details and authorities.
            // The credentials are set to null because we are not using them for authentication in this case,
            // as the JWT token has already been validated.
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            // We set the details of the authentication token using the WebAuthenticationDetailsSource,
            // which provides additional information about the request.
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
