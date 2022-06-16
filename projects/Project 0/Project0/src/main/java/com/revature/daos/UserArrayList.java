package com.revature.daos;

import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserArrayList implements UserDAOS{

    private List<User> userDB = new ArrayList<>();

    @Override
    public void insertUser(User u) {
        u.setUserId(userDB.size());
        userDB.add(u);
    }

    @Override
    public User getById(int id) {
        return userDB.get(id);
    }
}
