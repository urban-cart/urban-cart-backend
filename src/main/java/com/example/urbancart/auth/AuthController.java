package com.example.urbancart.auth;

import com.example.urbancart.auth.dto.AuthResponseDto;
import com.example.urbancart.auth.dto.ChangePasswordDto;
import com.example.urbancart.auth.dto.LoginDto;
import com.example.urbancart.auth.dto.RegisterDto;
import com.example.urbancart.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public AuthResponseDto login(@RequestBody @Valid LoginDto loginDto) {
    System.out.println("LoginDto: " + loginDto);
    return authService.login(loginDto);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/register")
  public User register(@RequestBody @Valid RegisterDto user) {
    return authService.register(user);
  }

  @PostMapping("/refresh")
  public AuthResponseDto refresh(HttpServletRequest request, HttpServletResponse response) {
    return authService.refresh(request, response);
  }

  @PatchMapping("/change-password")
  public User changePassword(
      @RequestBody @Valid ChangePasswordDto changePasswordDto, Principal connectedUser) {
    return authService.changePassword(changePasswordDto, connectedUser);
  }
}
