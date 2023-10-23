package com.example.urbancart.hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.urbancart.auth.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HelloControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private JwtService jwtService;

  @Test
  public void testHelloWorld() throws Exception {

    mockMvc
        .perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().json("{\"message\":\"Hello, World!\"}"));
  }

  @ParameterizedTest
  @CsvSource(
      value = {
        "World\tHello, World!",
        "John\tHello, John!",
        "Jane\tHello, Jane!",
        "J Doe\tHello, J Doe!"
      },
      delimiter = '\t')
  public void testHelloName(String name, String expected) throws Exception {
    mockMvc
        .perform(get("/hello").param("name", name))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.message").value(expected));
  }

  @Test
  public void testIgnoresExtraParameter() throws Exception {
    mockMvc
        .perform(get("/hello").param("name", "John").param("age", "42"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().json("{\"message\": \"Hello, John!\"}"));
  }

  @Test
  public void testXSS() throws Exception {
    mockMvc
        .perform(get("/hello").param("name", "<script>alert('XSS')</script>"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(
            content()
                .json(
                    "{\"message\": \"Hello, &lt;script&gt;alert(&#39;XSS&#39;)&lt;/script&gt;!\"}"));
  }
}
