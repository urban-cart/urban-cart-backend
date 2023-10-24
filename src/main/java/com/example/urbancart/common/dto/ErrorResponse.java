package com.example.urbancart.common.dto;

import java.util.List;

/// For GlobalExceptionHandler.java
public record ErrorResponse(Long timestamp, Integer status, String message, List<String> errors) {}
