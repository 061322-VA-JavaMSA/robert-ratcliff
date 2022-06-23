package com.revature.services;

import com.revature.daos.UserArrayList;
import com.revature.daos.UserDAOS;
import com.revature.daos.UserPostgreSQL;
import com.revature.models.User;

public class UserService {

    private UserDAOS ud = new UserPostgreSQL();

    public User createUser(User u) {
        ud.insertUser(u);
        return u;
    }

    public User getUserById(int id) {
        return ud.getById(id);
    }


    public User getUser(User u){
        return ud.getUser(u);
    }

    public boolean userIsEmployee(User u){
        return u.isEmployee();
    }

}