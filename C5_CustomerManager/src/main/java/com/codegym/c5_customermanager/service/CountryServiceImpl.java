package com.codegym.c5_customermanager.service;

import com.codegym.c5_customermanager.model.Country;
import com.codegym.c5_customermanager.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryServiceImpl implements CountryService{
    private static final String SELECT_ALL_COUNTRY = "SELECT id, name from country";
    private String jdbcURL = "jdbc:mysql://localhost:3306/c5_customermanager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Quangdv180729!!";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Country> findAll() throws SQLException {
        List<Country> list = new ArrayList<>();
        Connection connection = getConnection();
        try{
            Statement statement = connection.createStatement();

            System.out.println(this.getClass() + " findAll : " + statement);
            ResultSet rs = statement.executeQuery(SELECT_ALL_COUNTRY);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");


                Country country = new Country(id, name);
                list.add(country);
            }
            connection.close();
        }catch (SQLException sqlException){

        }
        return list;
    }

    @Override
    public void save(Country country) throws SQLException {

    }

    @Override
    public Country findById(int id) throws SQLException {
        String USER_BY_ID = "SELECT * FROM country where id = ?";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(USER_BY_ID);
        preparedStatement.setInt(1, id);

        System.out.println(this.getClass() + " findById : " + preparedStatement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id1 = rs.getInt("id");
            String name = rs.getString("name");


            Country country = new Country(id1, name);
            return country;
        }
        return null;
    }

    @Override
    public void update(int id, Country customer) throws SQLException {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void saveForRollBack(Customer customer) {

    }
}
