package com.revature.daos;

import com.revature.models.User;

public interface UserDAOS {

    void insertUser(User u);
    User getById(int id);
    User getUser(User u);
}
