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
    public List<Customer> findAll()  {

        List<Customer> list = new ArrayList<>();
        Connection connection = getConnection();
        try{
            Statement statement = connection.createStatement();

            System.out.println(this.getClass() + " findAll : " + statement);
            ResultSet rs = statement.executeQuery(SELECT_ALL_CUSTOMER);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int idCountry = rs.getInt("id_country");

                Customer cus = new Customer(id, name, email, address, idCountry);
                list.add(cus);
            }
            connection.close();
        }catch (SQLException sqlException){

        }
        return list;
    }

    @Override
    public void save(Customer customer) throws SQLException {
        String INSERT_CUSTOMER = "insert into customer(email, name, address, id_country) values (?, ?, ?,?)";
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER);
        ){
            preparedStatement.setString(1, customer.getEmail());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getIdCountry());

            System.out.println(this.getClass() + " save : " + preparedStatement);
            preparedStatement.execute();

        }catch(SQLException sql){
        }
    }

    @Override
    public void saveForRollBack(Customer customer) {
        String INSERT_CUSTOMER = "insert into customer(email, name, address) values (?, ?, ?)";
        Savepoint c5 = null;
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER);
                ){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, customer.getEmail());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());

            System.out.println(this.getClass() + " save : " + preparedStatement);

            preparedStatement.execute();        //Ngai
            c5 = connection.setSavepoint();

            preparedStatement.setString(1, customer.getEmail() + "x1"); // Ngai x1
            preparedStatement.execute();
            connection.rollback(c5);
            preparedStatement.setString(1, customer.getEmail() + "x2"); // Ngai x2
            preparedStatement.execute();    // Ngai x2
            preparedStatement.execute();    // Ngai x2
            connection.commit();
        }catch(SQLException sql){
            try {
                getConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


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
            int idCountry = rs.getInt("id_country");

            Customer cus = new Customer(id1, name, email, address, idCountry);
            return cus;
        }
        return null;
    }

    @Override
    public void update(int id, Customer customer) throws SQLException {
        String SP_UPDATE_CUSTOMER = "call c5_customermanager.sp_updatecustomer(?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        CallableStatement callableStatement = connection.prepareCall(SP_UPDATE_CUSTOMER);
        callableStatement.setInt(1, id);
        callableStatement.setString(2, customer.getEmail());
        callableStatement.setString(3, customer.getName());
        callableStatement.setString(4, customer.getAddress());

        callableStatement.registerOutParameter(5, Types.VARCHAR);

        System.out.println(this.getClass() + " update: " + callableStatement);
        callableStatement.execute();

        String message = callableStatement.getString(5);
        System.out.println("update......: " + message);
    }

    @Override
    public void remove(int id) {

    }
}
