package com.example.demo.Customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerJPADataAccessServiceTest {

    @InjectMocks
    private CustomerJPADataAccessService customerJPADataAccessService;

    @Mock
    private CustomerRepository mockCustomerRepository;

    private Customer customerOne, customerTwo;


    @BeforeEach
    void setUp() {
        Customer customerOne = new Customer("helio","helio@gmail.com",22,Gender.fromLabel("Male"));
        Customer customerTwo = new Customer("sandra","sandra@gmail.com",25,Gender.fromLabel("Female"));
    }


    @Test
    void selectAllCustomers() {
        //Given
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(customerOne);
        expectedCustomers.add(customerTwo);

        Mockito.when(mockCustomerRepository.findAll()).thenReturn(expectedCustomers);

        // When
        List<Customer> returnedCustomers = customerJPADataAccessService.selectAllCustomers();
        // Then
        assertEquals(expectedCustomers, returnedCustomers);
        Mockito.verify(mockCustomerRepository).findAll();
        Mockito.verifyNoMoreInteractions(mockCustomerRepository);
    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 1;
        // When
        customerJPADataAccessService.selectCustomerById(id);

        // Then
        Mockito.verify(mockCustomerRepository).findById(id);

    }

    @Test
    void insertCustomer() {
        // Given

        // When
        customerJPADataAccessService.insertCustomer(customerOne);
        // Then
        Mockito.verify(mockCustomerRepository).save(customerOne);
    }

    @Test
    void existsCustomerWithEmail() {
        // Given
        String email = "helio@gmail.com";
        // When
        customerJPADataAccessService.existsCustomerWithEmail(email);
        // Then
        Mockito.verify(mockCustomerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerByid() {
        // Given
        int id = 1;

        // When
        customerJPADataAccessService.existsCustomerByid(id);
        // Then
        Mockito.verify(mockCustomerRepository).existsCustomerByid(id);
    }

    @Test
    void deleteCustomerById() {
        // Given
        int id = 1;
        // When
        customerJPADataAccessService.deleteCustomerById(id);
        // Then
        Mockito.verify(mockCustomerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        // Given

        // When
        customerJPADataAccessService.updateCustomer(customerOne);
        // Then
        Mockito.verify(mockCustomerRepository).save(customerOne);
    }
}