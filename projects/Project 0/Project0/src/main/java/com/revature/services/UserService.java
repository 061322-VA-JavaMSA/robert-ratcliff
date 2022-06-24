package com.revature.services;

import com.revature.daos.UserArrayList;
import com.revature.daos.UserDAOS;
import com.revature.daos.UserPostgreSQL;
import com.revature.models.Item;
import com.revature.models.User;

import java.util.List;

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

    public List<Item> getOwnedItems(User u){return ud.getOwnedItems(u);}

}