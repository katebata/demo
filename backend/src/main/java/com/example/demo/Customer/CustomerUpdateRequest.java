package com.example.demo.Customer;

public record CustomerUpdateRequest(String name, String email, Integer age, Gender gender) {
}
