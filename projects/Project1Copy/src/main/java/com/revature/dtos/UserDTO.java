package com.revature.dtos;

import java.util.Objects;

import com.revature.models.Role;
import com.revature.models.User;

/*-
 * Data Transfer Objects
 */
public class UserDTO {

    private int id;
    private String username;
    private String userRole; //will change from String to Role after adding enum

    public UserDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserDTO(User u) {
        id = u.getUserId();
        username = u.getUsername();
        userRole = u.getRole();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", username=" + username + "]";
    }
}
