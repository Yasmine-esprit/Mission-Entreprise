package tn.esprit.spring.missionentreprise.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Skip filter for whitelisted paths
        if (request.getServletPath().startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Extract Authorization Header
        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader); // Debug log

        // 2. Validate Header Presence
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.err.println("Missing or invalid Authorization header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT token");
            return;
        }

        try {
            // 3. Extract and Validate Token
            final String jwt = authHeader.substring(7);
            System.out.println("Extracted JWT: " + jwt); // Debug log

            final String userEmail = jwtService.extractUsername(jwt);
            if (userEmail == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }

            // 4. Check Existing Authentication
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("User Authorities: " + userDetails.getAuthorities()); // Debug log

                // 5. Validate Token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authenticated user: " + userEmail); // Debug log
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("JWT processing error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authentication failed");
            return;
        }

        filterChain.doFilter(request, response);
    }
}