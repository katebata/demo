package com.example.demo.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerByid(Integer id);
    void deleteCustomerById(Integer id);
    void updateCustomer(Customer customer);
}




