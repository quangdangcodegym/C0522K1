package com.codegym.c5_customermanager.service;

import com.codegym.c5_customermanager.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    List<Customer> findAll() throws SQLException;

    void save(Customer customer) throws SQLException;

    Customer findById(int id) throws SQLException;

    void update(int id, Customer customer);

    void remove(int id);
}
