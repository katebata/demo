package com.example.demo.Customer;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    boolean existsCustomerByEmail(String email);

    boolean existsCustomerByid(Integer id);


}
