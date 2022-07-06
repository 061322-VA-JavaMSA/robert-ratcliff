package com.revature.daos;

import com.revature.models.Item;
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

    public User getUser(User u) {
        User verifiedUser = new User();
        String verifiedUsername;
        String verifiedPasswd;

        for(int i = 0; i < userDB.size(); i++){
            verifiedUser = userDB.get(i);
            verifiedUsername = verifiedUser.getUsername();
            verifiedPasswd = verifiedUser.getPassword();

            if((u.getUsername().equals(verifiedUsername))
            && (u.getPassword().equals(verifiedPasswd))){
                return u;
            }
        }
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User getByLogin(String username, String passwd) {
        return null;
    }

    @Override
    public List<Item> getOwnedItems(User u) {
        return null;
    }

    @Override
    public void makeOffer(User u, Item i, float amount) {

    }

    @Override
    public void acceptOffer(User u, Item i) {

    }
}
