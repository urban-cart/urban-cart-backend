package com.example.urbancart.user;

import com.example.urbancart.user.dto.ChangePasswordDto;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User changePassword(ChangePasswordDto request, Principal connectedUser) {
    User user = findByEmail(connectedUser.getName());
    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw new IllegalStateException("Old password is incorrect");
    }
    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
      throw new IllegalStateException("New password and confirm password do not match");
    }
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    return userRepository.save(user);
  }

  public User findByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("The credentials are incorrect"));
  }

  public User save(String email, String password) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new IllegalStateException("User with this email already exists");
    }
    return userRepository.save(user);
  }
}
