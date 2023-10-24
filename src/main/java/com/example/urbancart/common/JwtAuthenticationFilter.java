package com.example.urbancart.common;

import com.example.urbancart.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().startsWith("/auth")
        && !request.getRequestURI().equals("/auth/me")) {
      // If the request is for authentication, then skip the filter
      filterChain.doFilter(request, response);
      return;
    }
    String authHeader = request.getHeader("Authorization");
    System.out.println("Running the filter" + authHeader);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      System.out.println("Invalid token");
      filterChain.doFilter(request, response);
      return;
    }
    System.out.println("Valid token");
    String token = authHeader.substring(7); // The part after "Bearer "
    String userEmail = jwtService.extractEmail(token);

    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
    System.out.println("User details: " + userDetails);
    if (jwtService.isTokenValid(token, userDetails)) {
      System.out.println("Token is============= valid");
      var authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
