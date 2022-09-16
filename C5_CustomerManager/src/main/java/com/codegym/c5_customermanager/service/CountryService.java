package com.codegym.c5_customermanager.service;

import com.codegym.c5_customermanager.model.Country;
import com.codegym.c5_customermanager.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CountryService {
    List<Country> findAll() throws SQLException;

    void save(Country country) throws SQLException;

    Country findById(int id) throws SQLException;

    void update(int id, Country customer) throws SQLException;

    void remove(int id);

    public void saveForRollBack(Customer customer);
}
