package com.revature;

import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    static Scanner sc;
    static  AuthService as;
    static UserService us;
    //static StoreItems si; //will be used to test users being able to see items
    public static void main(String[] args){

        sc = new Scanner(System.in);
        as = new AuthService();
        us = new UserService();

        System.out.println("Welcome to the Store!");
        System.out.println("Select an option \n" +
                "-1: Register\n" +
                "-2: Login\n" +
                "-3: Test");
        String choice = sc.nextLine();

        switch(choice){
            case "1":
                //register user
                registerMenu();
                break;
            case "2":
                //login user
                loginMenuPostgres();
                //System.out.println("Working on logging");
                break;
            case "3":
                int id = 11;
                User u = us.getUserById(id);
                System.out.println(u);
                break;
            default:
                System.out.println("Sorry, I did not recognize that option");
        }
    }

    //Keeping code for now as logic, but will reimplement
    public static void registerMenu(){
        String username = null;
        String passwd = null;
        User newUser = new User(); //TODO cleanup this code to better handle new user.
        System.out.println("Register:");
        System.out.println("Please enter your username:");
        newUser.setUsername(sc.nextLine());
        System.out.println("Please enter your password:");
        newUser.setPassword(sc.nextLine());
        User createdUser = us.createUser(newUser);
        System.out.println("User: " + createdUser.getUsername() + " has been created.");
    }

    public static void loginMenu(){
        User unverifiedUser = new User();
        System.out.println("Login:");
        System.out.println("Please enter your username:");
        unverifiedUser.setUsername(sc.nextLine());
        System.out.println("Please enter your password:");
        unverifiedUser.setPassword(sc.nextLine());
        if(us.getUser(unverifiedUser) != null){
            System.out.println("Welcome Back!");
        }
        System.out.println("Sorry. We could not find a user with those credentials.");

    }

    public static void loginMenuPostgres(){
        User unverifiedUser = new User();
        System.out.println("Login:");
        System.out.println("Please enter your username:");
        unverifiedUser.setUsername(sc.nextLine());
        System.out.println("Please enter your password:");
        unverifiedUser.setPassword(sc.nextLine());
        if(us.getUser(unverifiedUser) != null){
            System.out.println("Welcome Back, " +unverifiedUser.getUsername()+"!");
        }else {
            System.out.println("Sorry. We could not find a user with those credentials.");
        }
    }
}