package com.example.urbancart.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public enum Role {
  USER(
      Set.of(
          Permission.READ_USER,
          Permission.CREATE_USER,
          Permission.UPDATE_USER,
          Permission.DELETE_USER,
          Permission.READ_ALL_CATEGORIES,
          Permission.READ_ALL_PRODUCTS,
          Permission.READ_PRODUCT,
          Permission.READ_ALL_ORDERS,
          Permission.CREATE_ORDER,
          Permission.READ_ORDER)),

  ADMIN(
      Set.of(
          Permission.READ_ALL_USERS,
          Permission.CREATE_ALL_USERS,
          Permission.UPDATE_ALL_USERS,
          Permission.DELETE_ALL_USERS,
          Permission.READ_ALL_CATEGORIES,
          Permission.CREATE_ALL_CATEGORIES,
          Permission.UPDATE_ALL_CATEGORIES,
          Permission.DELETE_ALL_CATEGORIES,
          Permission.READ_ALL_PRODUCTS,
          Permission.CREATE_ALL_PRODUCTS,
          Permission.UPDATE_ALL_PRODUCTS,
          Permission.DELETE_ALL_PRODUCTS,
          Permission.READ_ALL_ORDERS,
          Permission.CREATE_ALL_ORDERS,
          Permission.UPDATE_ALL_ORDERS,
          Permission.DELETE_ALL_ORDERS)),

  SUPER_ADMIN(
      Set.of(
          Permission.READ_ALL_USERS,
          Permission.CREATE_ALL_USERS,
          Permission.UPDATE_ALL_USERS,
          Permission.DELETE_ALL_USERS,
          Permission.READ_ALL_CATEGORIES,
          Permission.CREATE_ALL_CATEGORIES,
          Permission.UPDATE_ALL_CATEGORIES,
          Permission.DELETE_ALL_CATEGORIES,
          Permission.READ_ALL_PRODUCTS,
          Permission.CREATE_ALL_PRODUCTS,
          Permission.UPDATE_ALL_PRODUCTS,
          Permission.DELETE_ALL_PRODUCTS,
          Permission.READ_ALL_ORDERS,
          Permission.CREATE_ALL_ORDERS,
          Permission.UPDATE_ALL_ORDERS,
          Permission.DELETE_ALL_ORDERS));

  @Getter private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    return getPermissions().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
        .collect(Collectors.toList());
  }
}
