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
    private Role userRole;

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
    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && username.equals(userDTO.username) && userRole == userDTO.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
