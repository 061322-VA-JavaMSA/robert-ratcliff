package com.revature.models;

import com.revature.daos.UserDAOS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private int userId;
    private String username;
    private String password;
    private boolean isEmployee;
    private List<Item> ownedItems;


    public User(){
        super();
        ownedItems = new ArrayList<>();
    }

    public void insertUser(User u) {
        //do user stuff
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && isEmployee == user.isEmployee && username.equals(user.username) && password.equals(user.password) && Objects.equals(ownedItems, user.ownedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, isEmployee, ownedItems);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEmployee=" + isEmployee +
                ", ownedItems=" + ownedItems +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    public boolean isEmployee() { return isEmployee; }

    public void setEmployee(boolean employee) { isEmployee = employee; }

    public List<Item> getOwnedItems() { return ownedItems; }

    //need to either change this method or make a new one that adds an element.
    public void setOwnedItems(List<Item> ownedItems) { this.ownedItems = ownedItems; }

    public void addOwnedItems(Item i){ownedItems.add(i);}
}
