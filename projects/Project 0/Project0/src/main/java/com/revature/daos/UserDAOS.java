package com.revature.daos;

import com.revature.models.User;

public interface UserDAOS {

    void insertUser(User u);
    int getUserId(User u);
}
