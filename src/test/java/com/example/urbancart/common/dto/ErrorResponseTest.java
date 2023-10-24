package com.example.urbancart.common.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class ErrorResponseTest {

  @Test
  public void testErrorResponseRecord() {
    Long timestamp = System.currentTimeMillis();
    Integer status = 404;
    String message = "Not Found";
    List<String> errors = List.of("Error 1", "Error 2");

    ErrorResponse errorResponse = new ErrorResponse(timestamp, status, message, errors);

    // Verify the values using assertions
    assertEquals(timestamp, errorResponse.timestamp());
    assertEquals(status, errorResponse.status());
    assertEquals(message, errorResponse.message());
    assertEquals(errors, errorResponse.errors());

    // Ensure that toString() method is overridden
    assertNotNull(errorResponse.toString());
  }
}
