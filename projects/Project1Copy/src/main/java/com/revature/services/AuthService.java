package com.revature.services;

import com.revature.daos.UserDAOS;
import com.revature.daos.UserHibernate;
import com.revature.exceptions.LoginException;
import com.revature.models.User;

public class AuthService {

    private UserDAOS ud = new UserHibernate();

    public User login(String username, String password) throws LoginException {
        // if username/password passed are null, throws an exception
        if(username == null || password == null) {
            throw new LoginException();
        }

        User u = ud.getByLogin(username, password);
        // if no user of that name has been retrieved/if pass don't match, throw an exception
        if(u == null || !u.getPassword().equals(password)) {
            throw new LoginException();
        }
        return u;
    }
}
