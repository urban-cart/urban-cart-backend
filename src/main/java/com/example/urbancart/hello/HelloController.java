package com.example.urbancart.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/hello")
public class HelloController {

  @GetMapping
  public String helloWorld(@RequestParam(required = false, defaultValue = "World") String name) {
    name = HtmlUtils.htmlEscape(name);
    return String.format("Hello, %s!", name);
  }
}
