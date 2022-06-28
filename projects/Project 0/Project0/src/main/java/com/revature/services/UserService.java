package com.revature.services;

import com.revature.daos.*;
import com.revature.models.Item;
import com.revature.models.User;

import java.util.List;

public class UserService {

    private UserDAOS ud = new UserPostgreSQL();
    private SystemDAOS sd = new SystemPostgreSQL();

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

    public List<Item> getOwnedItems(User u){return ud.getOwnedItems(u);}
    public void makeOffer(User u, Item i, float amount){ud.makeOffer(u,i,amount);}
    public void acceptOffer(User u, Item i){ud.acceptOffer(u,i);}

}