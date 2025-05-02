package com.example.demo.Customer;

import com.example.demo.AbstractTestContainersDaoUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCAccessServiceTest extends AbstractTestContainersDaoUnitTest {

    private CustomerJDBCAccessService customerJDBCAccessService;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        customerJDBCAccessService = new CustomerJDBCAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );
        customerJDBCAccessService.insertCustomer(customer);

        // When
        List<Customer> customers = customerJDBCAccessService.selectAllCustomers();

        // Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        customerJDBCAccessService.insertCustomer(customer);

        int id = customerJDBCAccessService.selectAllCustomers().stream()
                .filter(cus -> cus.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();
        // When
        Optional<Customer> actualCustomer = customerJDBCAccessService.selectCustomerById(id);

        // Then

        assertThat(actualCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });

    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        customerJDBCAccessService.insertCustomer(customer);

        int id = customerJDBCAccessService.selectAllCustomers().stream()
                .filter(cus -> cus.getEmail().equals(customer.getEmail()))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();

        int falseId = id + 1;
        // When
        Optional<Customer> actualCustomer = customerJDBCAccessService.selectCustomerById(falseId);

        assertThat(actualCustomer).isEmpty();



    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        // When
        customerJDBCAccessService.insertCustomer(customer);

        // Then

        Optional<Customer> insertedCustomer = customerJDBCAccessService.selectAllCustomers()
                .stream()
                .filter(cus -> cus.getEmail().equals(customer.getEmail()))
                .findFirst();

        int insertedCustomerId = insertedCustomer.get().getId();

        assertThat(insertedCustomer).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getId()).isEqualTo(insertedCustomerId);
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                });
    }

    @Test
    void existsCustomerWithEmail() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        customerJDBCAccessService.insertCustomer(customer);

        // When
        boolean existsByEmail = customerJDBCAccessService.existsCustomerWithEmail(customer.getEmail());
        // Then
        assertTrue(existsByEmail);
    }

    @Test
    void existsCustomerByid() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        customerJDBCAccessService.insertCustomer(customer);

        int customerId = customerJDBCAccessService.selectAllCustomers()
                                .stream()
                .filter(cus -> cus.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When

        boolean existsById = customerJDBCAccessService.existsCustomerByid(customerId);
        // Then
        assertTrue(existsById);
    }

    @Test
    void deleteCustomerById() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        customerJDBCAccessService.insertCustomer(customer);

        int customerId = customerJDBCAccessService.selectAllCustomers()
                .stream()
                .filter(cus -> cus.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        customerJDBCAccessService.deleteCustomerById(customerId);
        // Then
        assertThat(customerJDBCAccessService.selectCustomerById(customerId).isEmpty()).isTrue();
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80)
        );

        customerJDBCAccessService.insertCustomer(customer);

        int customerId = customerJDBCAccessService.selectAllCustomers()
                .stream()
                .filter(cus -> cus.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        customer.setId(customerId);
        customer.setAge(30);
        // When
        customerJDBCAccessService.updateCustomer(customer);
        // Then
        assertThat(customerJDBCAccessService.selectCustomerById(customerId).get().getAge()).isEqualTo(30);
    }
}