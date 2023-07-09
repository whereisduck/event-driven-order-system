package customerservice.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CustomerNoFound extends ServiceApiException {


    public CustomerNoFound(String id) {
        super(NOT_FOUND, String.format("Customer with id %s does not exist", id));
    }
}
