package com.codegym.c5_customermanager.controller;

import com.codegym.c5_customermanager.model.Customer;
import com.codegym.c5_customermanager.security.UserRoleRequestWrapper;
import com.codegym.c5_customermanager.service.CustomerService;
import com.codegym.c5_customermanager.service.CustomerServiceImplMysql;
import com.codegym.c5_customermanager.service.UserService;
import com.codegym.c5_customermanager.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerServiceImplMysql();
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/customer1/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password= req.getParameter("password");

        if(userService.checkUsernamePassword(username, password)){
            // Lưu vô session
//            HttpSession httpSession = req.getSession();
//            httpSession.setAttribute("username", username);

            List<String> roles = new ArrayList<>();
            if(username.equals("admin")){
                roles.add("admin");
                roles.add("editor");
            }
            if(username.equals("user")){
                roles.add("user");
            }
            UserRoleRequestWrapper userRoleRequestWrapper = new UserRoleRequestWrapper(username, roles,req );
            List<Customer> customers = null;
            try {
                customers = this.customerService.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            userRoleRequestWrapper.setAttribute("customers", customers);



            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/customer1/list.jsp");
            requestDispatcher.forward(userRoleRequestWrapper, resp);

            ///resp.sendRedirect("/customers");
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/customer1/login.jsp");
            requestDispatcher.forward(req, resp);
        }

    }
}
