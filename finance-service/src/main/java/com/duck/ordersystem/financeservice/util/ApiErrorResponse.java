package com.duck.ordersystem.financeservice.util;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ApiErrorResponse {
  private final String message;
}
