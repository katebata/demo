package com.example.demo.Customer;

import com.example.demo.Exceptions.DuplicateRessourceException;
import com.example.demo.Exceptions.NoUpdateDetectedException;
import com.example.demo.Exceptions.RessourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new RessourceNotFoundException("Customer [%s] not found".formatted(id)));
    }

    public void insertCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        //check if email exists
        if(customerDao.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateRessourceException("Customer with email [%s] already exists".formatted(customerRegistrationRequest.email()));
        }
        //add
        customerDao.insertCustomer(new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        ));
    }

    public void deleteCustomerById(Integer id) {
        if(!customerDao.existsCustomerByid(id)){
            throw new RessourceNotFoundException("customer with id [%s] doesn't exist".formatted(id));
        }
        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {

        Customer customer = customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new RessourceNotFoundException("customer with id [%s] not found".formatted(customerId)));

        boolean changes = false;
        if(customerUpdateRequest.age() != null
                && !customer.getAge().equals(customerUpdateRequest.age())){
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if(customerUpdateRequest.name() != null
                && !customer.getName().equals(customerUpdateRequest.name())){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if(customerUpdateRequest.email() != null
                && !customer.getEmail().equals(customerUpdateRequest.email())){
            if(customerDao.existsCustomerWithEmail(customerUpdateRequest.email())){
                throw new DuplicateRessourceException("email already taken");
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }


        if(!changes){
            throw new NoUpdateDetectedException("No new data detected");
        }

        customerDao.updateCustomer(customer);
    }
}
