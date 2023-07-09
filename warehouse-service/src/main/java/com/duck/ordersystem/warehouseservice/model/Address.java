package com.duck.ordersystem.warehouseservice.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Address {
  //@NotEmpty
  private final String line1;
  private final String line2;
  //@NotEmpty
  private final String city;
  private final String county;
  //@NotEmpty
  private final String postcode;
  //@NotEmpty
  private final String country;
}
