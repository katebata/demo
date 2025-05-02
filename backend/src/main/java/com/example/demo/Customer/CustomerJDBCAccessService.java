package com.example.demo.Customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JDBC")
public class CustomerJDBCAccessService implements CustomerDao {


    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper;

    public CustomerJDBCAccessService(JdbcTemplate jdbcTemplate, RowMapper<Customer> customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;

    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT *
                FROM customer
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT * 
                FROM customer
                WHERE id = ?""";

        return jdbcTemplate.query(sql,customerRowMapper,id)
                .stream()
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?, ?, ?)
                """;
        int update = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());

        System.out.println(update);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {

        var sql = """
                SELECT *
                FROM customer
                WHERE email = ?""";

        return jdbcTemplate.query(sql,customerRowMapper,email)
                .stream()
                .findFirst()
                .isPresent();
    }

    @Override
    public boolean existsCustomerByid(Integer id) {
        var sql = """
                SELECT *
                FROM customer
                WHERE id = ?""";

        return jdbcTemplate.query(sql,customerRowMapper,id)
                .stream()
                .findFirst()
                .isPresent();
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE
                FROM customer
                WHERE id = ?""";

        jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = """
                UPDATE customer
                SET name = ?,
                    email = ?,
                    age = ?
                WHERE id = ?""";

        jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge(),
                customer.getId());
    }
}
