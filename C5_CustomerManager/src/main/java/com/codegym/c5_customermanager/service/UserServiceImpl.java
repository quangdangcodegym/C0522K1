package com.codegym.c5_customermanager.service;

public class UserServiceImpl implements UserService{
    @Override
    public boolean checkUsernamePassword(String username, String password) {

        if (username.equals("admin") && password.equals("123123")) {
            return true;
        }
        if (username.equals("user") && password.equals("123123")) {
            return true;
        }
        return false;
    }
}
