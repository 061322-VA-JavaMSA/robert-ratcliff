package com.revature.services;

import com.revature.daos.*;
import com.revature.models.User;

import java.util.List;

public class UserService {

    private UserDAOS ud = new UserHibernate();

    public void createUser(User u) {
        ud.insertUser(u);
    }

    public User getUserById(int id) {
        return ud.getById(id);
    }


    public User getUser(User u){
        return ud.getUser(u);
    }
    public User getByUsername(String username){return ud.getByUsername(username);}


    public List<User> getUsers(){return ud.getUsers();}

}