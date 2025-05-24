package com.example.demo.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRowMapperTest {

    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    CustomerRowMapper customerRowMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void mapRow() throws SQLException {
        // Given
        Customer expectedCustomer = new Customer(
                1,"helio","helio@gmail.com",22,Gender.fromLabel("Male")
        );
        // When
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("helio");
        when(mockResultSet.getString("email")).thenReturn("helio@gmail.com");
        when(mockResultSet.getInt("age")).thenReturn(22);
        when(mockResultSet.getString("gender")).thenReturn("Male");
        // Then

        Customer mappedCustomer = customerRowMapper.mapRow(mockResultSet, 1);

        assertEquals(expectedCustomer, mappedCustomer);
    }
}