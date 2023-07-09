package customerservice.service;

import customerservice.model.Customer;
import customerservice.repository.CustomerRepository;
import customerservice.util.CustomerNoFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    public Mono<Customer> findCustomerById(String id){
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNoFound(id)));
    }
}
