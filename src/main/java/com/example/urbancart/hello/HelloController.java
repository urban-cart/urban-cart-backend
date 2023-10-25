package com.example.urbancart.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class HelloController {

  public record HelloResponse(String message) {}

  @GetMapping(value = "/hello", produces = "application/json")
  public HelloResponse helloWorld(
      @RequestParam(required = false, defaultValue = "World") String name) {
    name = HtmlUtils.htmlEscape(name);
    return new HelloResponse(String.format("Hello, %s!", name));
  }
}
