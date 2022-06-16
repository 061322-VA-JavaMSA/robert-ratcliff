package com.revature.models;

import com.revature.daos.UserDAOS;

public abstract class User implements UserDAOS {

    private int userID;
    private String username;
    private String password;

    public abstract int getUserId(User user);

    public abstract void insertUser(User u);

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
