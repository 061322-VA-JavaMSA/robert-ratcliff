package com.revature.services;

import com.revature.daos.*;
import com.revature.models.Item;
import com.revature.models.User;

import java.util.List;

public class UserService {

    private UserDAOS ud = new UserPostgreSQL();
    private SystemDAOS sd = new SystemPostgreSQL();

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
    public float getWeeklyPayment(User u){return sd.calculateWeeklyPayment(u);}
    public float getItemPayment(User u, Item i){return sd.calculateItemPayment(u, i);}

}