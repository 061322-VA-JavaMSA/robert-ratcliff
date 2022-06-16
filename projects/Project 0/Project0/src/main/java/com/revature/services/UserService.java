package com.revature.services;

import com.revature.daos.UserArrayList;
import com.revature.daos.UserDAOS;
import com.revature.models.User;

public class UserService {

    private UserDAOS ud = new UserArrayList();

    //change this from return User to void
    public void createUser(User u) {
        //validation logic to object u being passed in...
        // business logic
        ud.insertUser(u);
    }

    public User getUserById(int id) {
        return ud.getById(id);
    }

}