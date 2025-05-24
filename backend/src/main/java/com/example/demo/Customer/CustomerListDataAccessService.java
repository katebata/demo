package com.example.demo.Customer;

import com.example.demo.Exceptions.RessourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    private static List<Customer> customers;

    static{
        customers = new ArrayList<>();
        Customer alex = new Customer(1,"alex","alex@gmail.com",21,Gender.fromLabel("Male"));
        Customer helio = new Customer(2,"helio","helio@gmail.com",23,Gender.fromLabel("Male"));
        customers.add(alex);
        customers.add(helio);
    }


    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(cus -> cus.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerByid(Integer id) {
        return customers.stream()
                .anyMatch(cus -> cus.getId().equals(id));
    }


    @Override
    public void deleteCustomerById(Integer id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer customer) {
        Customer customerToUpdate = customers.stream().filter(cus -> cus.getId()
                .equals(customer.getId()))
                .findFirst()
                .orElseThrow(() -> new RessourceNotFoundException("customer with id "+ customer.getId()+" doesn't exist"));

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setAge(customer.getAge());
        customerToUpdate.setGender(customer.getGender());
    }
}
