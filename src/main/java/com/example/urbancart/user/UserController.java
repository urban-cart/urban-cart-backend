package com.example.urbancart.user;

import com.example.urbancart.user.dto.ChangePasswordDto;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PatchMapping("/change-password")
  public User changePassword(
      @RequestBody @Valid ChangePasswordDto changePasswordDto, Principal connectedUser) {
    return userService.changePassword(changePasswordDto, connectedUser);
  }
}
