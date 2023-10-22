package com.example.urbancart.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Table(name = "users")
public class User implements UserDetails {
  @Id @GeneratedValue private UUID id;

  @Column(nullable = false, unique = true)
  private String email;

  @JsonIgnore private String password;

  private Boolean isVerified = Boolean.FALSE;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isVerified;
  }

  @PrePersist
  @PreUpdate
  public void prePersist() {
    if (this.email == null) throw new IllegalArgumentException("Email cannot be null");
    this.email = this.email.toLowerCase();
  }
}
