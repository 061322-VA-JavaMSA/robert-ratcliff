package com.revature.services;

import com.revature.daos.UserArrayList;
import com.revature.daos.UserDAOS;
import com.revature.models.User;

public class UserService {

    private UserDAOS ud = new UserArrayList();

    //change this from return User to void
    public User createUser(User u) {
        //validation logic to object u being passed in...
        // business logic
        ud.insertUser(u);
        return u;
    }

    public User getUserById(int id) {
        return ud.getById(id);
    }

    //This is encapsulating getting the user to the file that needs to handle the database.
    public User getUser(User u){
        return ud.getUser(u);
    }

}