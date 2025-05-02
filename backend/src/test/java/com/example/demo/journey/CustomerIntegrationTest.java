package com.example.demo.journey;

import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerRegistrationRequest;
import com.example.demo.Customer.CustomerUpdateRequest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void canRegisterACustomer() {
        // Create registration request

        Faker faker = new Faker();
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                faker.name().firstName(),
                "test123" + faker.internet().safeEmailAddress(),
                faker.number().numberBetween(18, 80)
        );

        // send a post request

        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registrationRequest), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customer

        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        // make sure that the previously added customer is present

        Customer expectedCustomer = new Customer(
                registrationRequest.name(),
                registrationRequest.email(),
                registrationRequest.age()
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        // get customer by id

        var id = allCustomers.stream()
                .filter(cus -> cus.getEmail().equals(registrationRequest.email()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(); //todo

        expectedCustomer.setId(id);

        webTestClient.get()
                .uri("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);

    }

    @Test
    void canDeleteCustomer() {
        // Create registration request

        Faker faker = new Faker();
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                faker.name().firstName(),
                "test123" + faker.internet().safeEmailAddress(),
                faker.number().numberBetween(18, 80)
        );

        // send a post request

        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registrationRequest), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customer

        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        // make sure that the previously added customer is present

        Customer expectedCustomer = new Customer(
                registrationRequest.name(),
                registrationRequest.email(),
                registrationRequest.age()
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        // get customer's id

        var id = allCustomers.stream()
                .filter(cus -> cus.getEmail().equals(registrationRequest.email()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(); //todo

        expectedCustomer.setId(id);

        // delete Customer

        webTestClient.delete()
                .uri("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // check that the client was deleted

        webTestClient.get()
                .uri("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        // add a customer

        Faker faker = new Faker();
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                faker.name().firstName(),
                "test123" + faker.internet().safeEmailAddress(),
                faker.number().numberBetween(18, 80)
        );

        webTestClient.post()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registrationRequest), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get all customer

        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        // make sure that the previously added customer is present

        Customer expectedCustomer = new Customer(
                registrationRequest.name(),
                registrationRequest.email(),
                registrationRequest.age()
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        // get added customer's id

        var id = allCustomers.stream()
                .filter(cus -> cus.getEmail().equals(registrationRequest.email()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        expectedCustomer.setId(id);

        // create a CustomerUpdateRequest

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                faker.name().lastName(),
                faker.internet().safeEmailAddress(),
                faker.number().numberBetween(18, 80)
        );

        // update the customer

        webTestClient.put()
                .uri("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // check that the customer was updated

        Customer updatedCustomer = webTestClient.get()
                .uri("/api/v1/customers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        assertEquals(updatedCustomer.getEmail(), updateRequest.email());
        assertEquals(updatedCustomer.getAge(), updateRequest.age());
        assertEquals(updatedCustomer.getName(), updateRequest.name());

    }
}
