package com.revature.daos;

import com.revature.models.User;
import java.sql.*; //change to specifics later.

public class UserPostgreSQL implements UserDAOS{

    @Override
    public void insertUser(User u) {

    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User getUser(User u) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User getByLogin(String username, String passwd) {
        return null;
    }
}
