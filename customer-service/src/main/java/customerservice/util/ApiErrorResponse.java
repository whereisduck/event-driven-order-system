package customerservice.util;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ApiErrorResponse {
  private final String message;
}
