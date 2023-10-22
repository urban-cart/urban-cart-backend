package com.example.urbancart.auth;

import com.example.urbancart.auth.dto.AuthResponseDto;
import com.example.urbancart.auth.dto.LoginDto;
import com.example.urbancart.auth.dto.RegisterDto;
import com.example.urbancart.user.User;
import com.example.urbancart.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponseDto login(LoginDto loginDto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    var user = userService.findByEmail(loginDto.getEmail());
    return jwtService.generateTokens(user);
  }

  public AuthResponseDto refresh(HttpServletRequest request, HttpServletResponse response) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Invalid token");
    }
    refreshToken = authHeader.substring(7); // The part after "Bearer "
    userEmail = jwtService.extractEmail(refreshToken);
    var user = userService.findByEmail(userEmail);
    if (user == null) {
      throw new IllegalArgumentException("Invalid token");
    }
    return jwtService.generateTokens(user);
  }

  public User register(@Valid RegisterDto user) {
    return userService.save(user.getEmail(), user.getPassword());
  }
}
