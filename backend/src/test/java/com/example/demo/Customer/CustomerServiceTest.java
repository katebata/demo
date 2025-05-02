package com.example.demo.Customer;

import com.example.demo.Exceptions.DuplicateRessourceException;
import com.example.demo.Exceptions.NoUpdateDetectedException;
import com.example.demo.Exceptions.RessourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerDao customerDaoMock;

    @InjectMocks
    CustomerService customerService;

    private Customer customerOne, customerTwo;

    private CustomerUpdateRequest customerUpdateRequest;

    @BeforeEach
    void setUp() {
        customerOne = new Customer("helio","helio@gmail.com",22);
        customerTwo = new Customer("sandra","sandra@gmail.com",25);
        customerUpdateRequest = new CustomerUpdateRequest(
                customerOne.getName(),
                customerOne.getEmail(),
                customerOne.getAge()
        );
    }

    @Test
    void getAllCustomers() {
        // Given
        List<Customer> expectedCustomers = Arrays.asList(customerOne,customerTwo);
        // When
        Mockito.when(customerDaoMock.selectAllCustomers()).thenReturn(expectedCustomers);
        // Then
        List<Customer> actualCustomers = customerService.getAllCustomers();
        assertEquals(expectedCustomers, actualCustomers);
        Mockito.verify(customerDaoMock).selectAllCustomers();
        Mockito.verifyNoMoreInteractions(customerDaoMock);
    }

    @Test
    void getCustomerById() {
        // Given
        Integer id = 1;
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerOne));
        // Then

        Customer actualCustomer = customerService.getCustomerById(id);
        assertEquals(customerOne, actualCustomer);
        Mockito.verify(customerDaoMock).selectCustomerById(id);
        Mockito.verifyNoMoreInteractions(customerDaoMock);
    }

    @Test
    void getCustomerByIdFailsWhenCustomerDoesNotExist() {
        // Given
        Integer falseId = -1;
        // When
        Mockito.when(customerDaoMock.selectCustomerById(falseId)).thenReturn(Optional.empty());
        // Then
        assertThatThrownBy(() -> customerService.getCustomerById(falseId)).isInstanceOf(RessourceNotFoundException.class)
                .hasMessage("Customer [%s] not found".formatted(falseId));
        Mockito.verify(customerDaoMock).selectCustomerById(falseId);
        Mockito.verifyNoMoreInteractions(customerDaoMock);
    }

    @Test
    void insertCustomer() {
        // Given

        // When
        Mockito.when(customerDaoMock.existsCustomerWithEmail(customerOne.getEmail())).thenReturn(false);
        // Then
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
                customerOne.getName(),
                customerOne.getEmail(),
                customerOne.getAge());

        customerService.insertCustomer(customerRegistrationRequest);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );

        Mockito.verify(customerDaoMock).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        customerOne.setId(capturedCustomer.getId());
        assertEquals(customerOne, capturedCustomer);
        Mockito.verifyNoMoreInteractions(customerDaoMock);
    }

    @Test
    void insertCustomerFailsWhenEmailExists() {
        // Given

        // When
        Mockito.when(customerDaoMock.existsCustomerWithEmail(customerOne.getEmail())).thenReturn(true);
        // Then

        assertThatThrownBy(() -> customerService.insertCustomer(new CustomerRegistrationRequest(
                customerOne.getName(),
                customerOne.getEmail(),
                customerOne.getAge())))
                .isInstanceOf(DuplicateRessourceException.class)
                        .hasMessage("Customer with email [%s] already exists".formatted(customerOne.getEmail()));

    }

    @Test
    void deleteCustomerById() {
        // Given
        int id = 1;
        // When
        Mockito.when(customerDaoMock.existsCustomerByid(id)).thenReturn(true);
        // Then
        customerService.deleteCustomerById(id);

        Mockito.verify(customerDaoMock).deleteCustomerById(id);
        Mockito.verifyNoMoreInteractions(customerDaoMock);
    }

    @Test
    void deleteCustomerByIdFailsWhenCustomerDoesNotExist() {
        // Given
        int id = 1;
        // When
        Mockito.when(customerDaoMock.existsCustomerByid(id)).thenReturn(false);
        // Then

        assertThatThrownBy(() -> customerService.deleteCustomerById(id))
                .isInstanceOf(RessourceNotFoundException.class)
                .hasMessage("customer with id [%s] doesn't exist".formatted(id));
    }

    @Test
    void updateCustomerAllFieldsUpdated() {
        // Given
        int id = 1;
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerTwo));
        Mockito.when(customerDaoMock.existsCustomerWithEmail(customerUpdateRequest.email())).thenReturn(false);
        // Then
        customerService.updateCustomer(id,customerUpdateRequest);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDaoMock).updateCustomer(customerCaptor.capture());
        Customer capturedCustomer = customerCaptor.getValue();

        assertEquals(capturedCustomer.getId(), customerTwo.getId());
        assertEquals(capturedCustomer.getEmail(),customerUpdateRequest.email());
        assertEquals(capturedCustomer.getName(),customerUpdateRequest.name());
        assertEquals(capturedCustomer.getAge(),customerUpdateRequest.age());

    }

    @Test
    void updateCustomerNameUpdated() {
        // Given
        int id = 1;
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                "Karim",
                null,
                null
        );
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerTwo));
        // Then
        customerService.updateCustomer(id,customerUpdateRequest);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDaoMock).updateCustomer(customerCaptor.capture());
        Customer capturedCustomer = customerCaptor.getValue();

        assertEquals(capturedCustomer.getName(),customerUpdateRequest.name());

        assertEquals(capturedCustomer.getId(), customerTwo.getId());
        assertEquals(capturedCustomer.getEmail(),customerTwo.getEmail());
        assertEquals(capturedCustomer.getAge(),customerTwo.getAge());

    }

    @Test
    void updateCustomerEmailUpdated() {
        // Given
        int id = 1;
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                null,
                "Karim@gmail.com",
                null
        );
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerTwo));
        Mockito.when(customerDaoMock.existsCustomerWithEmail(customerUpdateRequest.email())).thenReturn(false);
        // Then
        customerService.updateCustomer(id,customerUpdateRequest);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDaoMock).updateCustomer(customerCaptor.capture());
        Customer capturedCustomer = customerCaptor.getValue();

        assertEquals(capturedCustomer.getEmail(),customerUpdateRequest.email());

        assertEquals(capturedCustomer.getId(), customerTwo.getId());
        assertEquals(capturedCustomer.getName(),customerTwo.getName());
        assertEquals(capturedCustomer.getAge(),customerTwo.getAge());

    }

    @Test
    void updateCustomerAgeUpdated() {
        // Given
        int id = 1;
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
                null,
                null,
                39
        );
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerTwo));
        // Then
        customerService.updateCustomer(id,customerUpdateRequest);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDaoMock).updateCustomer(customerCaptor.capture());
        Customer capturedCustomer = customerCaptor.getValue();

        assertEquals(capturedCustomer.getAge(),customerUpdateRequest.age());

        assertEquals(capturedCustomer.getId(), customerTwo.getId());
        assertEquals(capturedCustomer.getName(),customerTwo.getName());
        assertEquals(capturedCustomer.getEmail(),customerTwo.getEmail());

    }


    @Test
    void updateCustomerFailsWhenEmailAlreadyExists() {
        // Given
        int id = 1;
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerTwo));
        Mockito.when(customerDaoMock.existsCustomerWithEmail(customerUpdateRequest.email())).thenReturn(true);
        // Then
        assertThatThrownBy(() -> customerService.updateCustomer(id,customerUpdateRequest))
                .isInstanceOf(DuplicateRessourceException.class)
                .hasMessage("email already taken");

        Mockito.verify(customerDaoMock, never()).updateCustomer(any());
    }


    @Test
    void updateCustomerFailsWhenCustomerDoesNotExist() {
        // Given
        int id = 1;
        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.empty());
        // Then
        assertThatThrownBy(() -> customerService.updateCustomer(id,customerUpdateRequest))
                .isInstanceOf(RessourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));


        Mockito.verify(customerDaoMock, never()).updateCustomer(any());
    }

    @Test
    void updateCustomerFailsWhenNoNewDataWasFound() {
        // Given
        int id = 1;

        // When
        Mockito.when(customerDaoMock.selectCustomerById(id)).thenReturn(Optional.of(customerOne));
        // Then

        assertThatThrownBy(() -> customerService.updateCustomer(id, customerUpdateRequest))
                .isInstanceOf(NoUpdateDetectedException.class)
                .hasMessage("No new data detected");


        Mockito.verify(customerDaoMock, never()).updateCustomer(any());
    }
}