package customerservice.controller;

import customerservice.model.Customer;
import customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/{id}")
    public Mono<Customer> findCustomerById(@PathVariable String id){
        return customerService.findCustomerById(id);
    }
}
