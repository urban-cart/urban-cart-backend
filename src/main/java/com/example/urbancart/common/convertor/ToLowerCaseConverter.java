package com.example.urbancart.common.convertor;

import com.fasterxml.jackson.databind.util.StdConverter;

public class ToLowerCaseConverter extends StdConverter<String, String> {
  @Override
  public String convert(String value) {
    if (value == null) {
      return null;
    }
    return value.toLowerCase();
  }
}
