package com.example.demo.Customer;

import com.example.demo.AbstractTestContainersDaoUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainersDaoUnitTest {

    @Autowired
    private CustomerRepository underTest;


    @BeforeEach
    void setUp() {
    }

    @Test
    void existsCustomerByEmail() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80),
                Gender.fromLabel(faker.demographic().sex())
        );
        underTest.save(customer);
        // When
        boolean emailExists = underTest.existsCustomerByEmail(customer.getEmail());
        // Then
        assertTrue(emailExists);
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailDoesNotExist() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80),
                Gender.fromLabel(faker.demographic().sex())
        );
        underTest.save(customer);
        // When
        boolean emailExists = underTest.existsCustomerByEmail(customer.getEmail()+ "rand");
        // Then
        assertFalse(emailExists);
    }

    @Test
    void existsCustomerByid() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "_" + UUID.randomUUID(),
                faker.number().numberBetween(18, 80),
                Gender.fromLabel(faker.demographic().sex())
        );
        underTest.save(customer);

        Integer id = underTest.findAll().stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When
        boolean customerExistsById = underTest.existsCustomerByid(id);
        // Then
        assertTrue(customerExistsById);
    }

    @Test
    void existsCustomerByidFailsWhenIdDoesNotExist() {
        // Given
        Integer id = -1;
        // When
        boolean customerExistsById = underTest.existsCustomerByid(id);
        // Then
        assertFalse(customerExistsById);
    }
}