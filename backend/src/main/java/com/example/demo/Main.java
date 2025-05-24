package com.example.demo;

import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Customer.Gender;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Random;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository) {

		return args -> {
			Faker faker = new Faker();
			Random random = new Random();
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			Gender gender = Gender.fromLabel(faker.demographic().sex());

			Customer customer= new Customer(firstName+ " " + lastName,
					firstName.toLowerCase() + "." + lastName.toLowerCase()
					+ "@gmail.com",
					random.nextInt(16,99),
					gender);

			customerRepository.save(customer);
		};
	}
}
