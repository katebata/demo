package com.example.demo.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable Integer customerId) {
        return customerService.getCustomerById(customerId);

    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        customerService.insertCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }


}
