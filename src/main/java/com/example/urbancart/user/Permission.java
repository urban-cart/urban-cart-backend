package com.example.urbancart.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
  READ_ALL_USERS("read:all_users"),
  CREATE_ALL_USERS("create:all_users"),
  UPDATE_ALL_USERS("update:all_users"),
  DELETE_ALL_USERS("delete:all_users"),

  READ_USER("read:user"),
  CREATE_USER("create:user"),
  UPDATE_USER("update:user"),
  DELETE_USER("delete:user"),

  READ_ALL_PRODUCTS("read:all_products"),
  CREATE_ALL_PRODUCTS("create:all_products"),
  UPDATE_ALL_PRODUCTS("update:all_products"),
  DELETE_ALL_PRODUCTS("delete:all_products"),

  READ_PRODUCT("read:product"),
  CREATE_PRODUCT("create:product"),
  UPDATE_PRODUCT("update:product"),
  DELETE_PRODUCT("delete:product"),

  READ_ALL_CATEGORIES("read:all_categories"),
  CREATE_ALL_CATEGORIES("create:all_categories"),
  UPDATE_ALL_CATEGORIES("update:all_categories"),
  DELETE_ALL_CATEGORIES("delete:all_categories"),

  READ_CATEGORY("read:category"),
  CREATE_CATEGORY("create:category"),
  UPDATE_CATEGORY("update:category"),
  DELETE_CATEGORY("delete:category"),

  READ_ALL_ORDERS("read:all_orders"),
  CREATE_ALL_ORDERS("create:all_orders"),
  UPDATE_ALL_ORDERS("update:all_orders"),
  DELETE_ALL_ORDERS("delete:all_orders"),

  READ_ORDER("read:order"),
  CREATE_ORDER("create:order"),
  UPDATE_ORDER("update:order"),
  DELETE_ORDER("delete:order");

  @Getter private final String permission;
}
