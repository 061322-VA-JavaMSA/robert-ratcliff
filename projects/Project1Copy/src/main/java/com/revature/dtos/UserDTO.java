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
    private String firstName;
    private String lastName;
    private String email;
    private Role userRole;

    public UserDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public UserDTO(User u) {
        id = u.getUserId();
        username = u.getUsername();
        firstName = u.getFirstName();
        lastName = u.getLastName();
        email = u.getEmail();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && username.equals(userDTO.username) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(email, userDTO.email) && userRole == userDTO.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, userRole);
    }

}
