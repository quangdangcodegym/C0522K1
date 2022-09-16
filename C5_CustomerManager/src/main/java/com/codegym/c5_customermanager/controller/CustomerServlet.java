package com.codegym.c5_customermanager.controller;


import com.codegym.c5_customermanager.model.Country;
import com.codegym.c5_customermanager.model.Customer;
import com.codegym.c5_customermanager.service.CountryService;
import com.codegym.c5_customermanager.service.CountryServiceImpl;
import com.codegym.c5_customermanager.service.CustomerService;
import com.codegym.c5_customermanager.service.CustomerServiceImplMysql;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet implements Serializable {

    CustomerService customerService;
    CountryService countryService;
    List<Country> countries;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImplMysql();
        countryService = new CountryServiceImpl();
        try {
            countries = countryService.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                try {
                    createCustomer(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                try {
                    updateCustomer(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                break;
            default:
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                try {
                    showEditForm(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete":
                break;
            case "view":
                break;
            default:
                try {
                    listCustomers(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }



    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        List<Customer> customers = this.customerService.findAll();
        request.setAttribute("customers", customers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/customer1/list.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/customer1/create.jsp");
        request.setAttribute("countries", countries);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<String> errors = new ArrayList<>();
        Customer customer = new Customer();
        RequestDispatcher dispatcher = null;
        try {
            String name = request.getParameter("name");
            customer.setName(name);
            String email = request.getParameter("email");
            customer.setEmail(email);
            String address = request.getParameter("address");
            customer.setAddress(address);
            int idCountry = Integer.parseInt(request.getParameter("idCountry"));

            customer.setIdCountry(idCountry);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
            dispatcher = request.getRequestDispatcher("/WEB-INF/customer1/create.jsp");

            if (!constraintViolations.isEmpty()) {
                for (ConstraintViolation<Customer> item : constraintViolations) {
                    errors.add(item.getMessage());
                }
                request.setAttribute("errors", errors);
                request.setAttribute("customer", customer);
                request.setAttribute("countries", countries);
            } else {
                // Vao database kiem tra idCountry co hop le hay khong
                if (countryService.findById(idCountry) != null) {
                    this.customerService.save(customer);
                    request.setAttribute("message", "New customer was created");
                } else {
                    errors.add("Country khong hop le");
                    request.setAttribute("errors", errors);
                    request.setAttribute("customer", customer);
                    request.setAttribute("countries", countries);
                }
            }
            dispatcher.forward(request, response);

        }catch (NumberFormatException numberFormatException){
            dispatcher = request.getRequestDispatcher("/WEB-INF/customer1/create.jsp");
            errors.add("Loi format...");
            request.setAttribute("errors", errors);
            request.setAttribute("countries", countries);
            request.setAttribute("customer", customer);
            dispatcher.forward(request, response);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = this.customerService.findById(id);
        RequestDispatcher dispatcher;
        if(customer == null){
            dispatcher = request.getRequestDispatcher("error-404.jsp");
        } else {
            request.setAttribute("customer", customer);
            dispatcher = request.getRequestDispatcher("customer/edit.jsp");
        }
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Customer customer = this.customerService.findById(id);
        RequestDispatcher dispatcher;
        if(customer == null){
            dispatcher = request.getRequestDispatcher("error-404.jsp");
        } else {
            customer.setName(name);
            customer.setEmail(email);
            customer.setAddress(address);
            this.customerService.update(id, customer);
            request.setAttribute("customer", customer);
            request.setAttribute("message", "Customer information was updated");
            dispatcher = request.getRequestDispatcher("customer/edit.jsp");
        }
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
