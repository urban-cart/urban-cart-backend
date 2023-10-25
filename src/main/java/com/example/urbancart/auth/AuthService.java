package com.example.urbancart.auth;

import com.example.urbancart.auth.dto.AuthResponseDto;
import com.example.urbancart.auth.dto.ChangePasswordDto;
import com.example.urbancart.auth.dto.LoginDto;
import com.example.urbancart.auth.dto.RegisterDto;
import com.example.urbancart.user.User;
import com.example.urbancart.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  public AuthResponseDto login(LoginDto loginDto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
    var user = findByEmail(loginDto.getEmail());
    return jwtService.generateTokens(user);
  }

  public AuthResponseDto refresh(HttpServletRequest request, HttpServletResponse response) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
    refreshToken = authHeader.substring(7); // The part after "Bearer "
    userEmail = jwtService.extractEmail(refreshToken, true);
    var user = findByEmail(userEmail);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }
    return jwtService.generateTokens(user);
  }

  public User register(@Valid RegisterDto register) {
    User user = new User();
    user.setEmail(register.getEmail());
    user.setPassword(passwordEncoder.encode(register.getPassword()));
    user.setRole(register.getRole());
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "The email is already used by another user");
    }
    return userRepository.save(user);
  }

  public void changePassword(ChangePasswordDto request, Principal connectedUser) {
    User user = findByEmail(connectedUser.getName());
    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw new IllegalStateException("Old password is incorrect");
    }
    if (!request.getPassword().equals(request.getConfirmPassword())) {
      throw new IllegalStateException("New password and confirm password do not match");
    }
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
  }

  public User findByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Email"));
  }
}
