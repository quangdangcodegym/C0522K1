package com.codegym.c5_customermanager.service;

import com.codegym.c5_customermanager.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImplMysql implements CustomerService{

    private String jdbcURL = "jdbc:mysql://localhost:3306/c5_customermanager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Quangdv180729!!";


    String SELECT_ALL_CUSTOMER = "SELECT * FROM customer";








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
    public List<Customer> findAll() throws SQLException {

        List<Customer> list = new ArrayList<>();

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        System.out.println(this.getClass() + " findAll : " + statement);
        ResultSet rs = statement.executeQuery(SELECT_ALL_CUSTOMER);
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String address = rs.getString("address");

            Customer cus = new Customer(id, name, email, address);
            list.add(cus);
        }

        return list;
    }

    @Override
    public void save(Customer customer) throws SQLException {
        String INSERT_CUSTOMER = "insert into customer(email, name, address) values (?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER);
        preparedStatement.setString(1, customer.getEmail());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getAddress());

        System.out.println(this.getClass() + " save : " + preparedStatement);

        preparedStatement.execute();
    }

    @Override
    public Customer findById(int id) throws SQLException {
        String USER_BY_ID = "SELECT * FROM customer where id = ?";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(USER_BY_ID);
        preparedStatement.setInt(1, id);

        System.out.println(this.getClass() + " findById : " + preparedStatement);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int id1 = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String address = rs.getString("address");

            Customer cus = new Customer(id1, name, email, address);
            return cus;
        }
        return null;
    }

    @Override
    public void update(int id, Customer customer) {

    }

    @Override
    public void remove(int id) {

    }
}
