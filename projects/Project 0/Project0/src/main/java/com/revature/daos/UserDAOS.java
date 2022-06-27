package com.revature.daos;

import com.revature.models.Item;
import com.revature.models.User;

import java.util.List;

public interface UserDAOS {

    void insertUser(User u);
    User getById(int id);
    User getUser(User u);
    User getByUsername(String username);
    User getByLogin(String username, String passwd);
    List<Item> getOwnedItems(User u);
    void makeOffer(User u, Item i, float amount);
    public void acceptOffer(User u, Item i);

}
