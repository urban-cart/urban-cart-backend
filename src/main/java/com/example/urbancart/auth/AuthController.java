package com.example.urbancart.auth;

import com.example.urbancart.auth.dto.AuthResponseDto;
import com.example.urbancart.auth.dto.ChangePasswordDto;
import com.example.urbancart.auth.dto.LoginDto;
import com.example.urbancart.auth.dto.RegisterDto;
import com.example.urbancart.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class AuthController {

  private final AuthService authService;
  private final ModelMapper modelMapper;

  @PostMapping(value = "/login", produces = "application/json")
  public AuthResponseDto login(@RequestBody @Valid LoginDto loginDto) {
    return authService.login(loginDto);
  }

  @PostMapping(value = "/register", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto register(@RequestBody @Valid RegisterDto user) {
    var data = authService.register(user);
    return modelMapper.map(data, UserResponseDto.class);
  }

  @PostMapping(value = "/refresh", produces = "application/json")
  public AuthResponseDto refresh(HttpServletRequest request, HttpServletResponse response) {
    return authService.refresh(request, response);
  }

  @PatchMapping("/change-password")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(
      @RequestBody @Valid ChangePasswordDto changePasswordDto, Principal connectedUser) {
    authService.changePassword(changePasswordDto, connectedUser);
  }

  @GetMapping(value = "/me", produces = "application/json")
  public UserResponseDto me(Principal connectedUser) {
    var user = authService.findByEmail(connectedUser.getName());
    return modelMapper.map(user, UserResponseDto.class);
  }
}
