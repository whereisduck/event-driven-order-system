package com.duck.ordersystem.financeservice.model;


import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PaymentDetails {
  //@NotEmpty
  private final String cardType;
  //@NotEmpty
  private final String nameOnCard;
  //@Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$")
  private final String cardNumber;
  //@Min(100)
  //@Max(999)
  private final Integer cvv;
  //@Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$")
  private final String expires;
}
