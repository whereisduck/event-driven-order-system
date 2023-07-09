package com.duck.ordersystem.financeservice.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
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
