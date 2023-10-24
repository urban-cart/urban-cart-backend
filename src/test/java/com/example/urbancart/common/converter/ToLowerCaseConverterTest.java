package com.example.urbancart.common.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.urbancart.common.convertor.ToLowerCaseConverter;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.junit.jupiter.api.Test;

public class ToLowerCaseConverterTest {

  private final StdConverter<String, String> converter = new ToLowerCaseConverter();

  @Test
  public void testConvertLowerCase() {
    String input = "HelloWorld";
    String result = converter.convert(input);
    assertEquals("helloworld", result);
  }

  @Test
  public void testConvertNull() {
    String result = converter.convert(null);
    assertNull(result);
  }

  @Test
  public void testConvertEmptyString() {
    String input = "";
    String result = converter.convert(input);
    assertEquals("", result);
  }

  @Test
  public void testConvertMixedCase() {
    String input = "AbCdEf";
    String result = converter.convert(input);
    assertEquals("abcdef", result);
  }
}
