package com.revature;

import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.ItemService;
import com.revature.services.UserService;

import java.util.List;
import java.util.Scanner;

public class Driver {

    static Scanner sc;
    static AuthService as;
    static UserService us;
    static User u;
    static ItemService itemService;

    //static StoreItems si; //will be used to test users being able to see items
    public static void main(String[] args) {

        sc = new Scanner(System.in);
        as = new AuthService();
        us = new UserService();
        u = new User();
        itemService = new ItemService();
        startMenu();
    }

    public static void startMenu() {
        System.out.println("Welcome to the Store!");
        System.out.println("Select an option \n" +
                "-1: Register\n" +
                "-2: Login\n" +
                "-3: Employee\n" +
                "-4: Quit");
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                //register user
                registerMenu();
                homeMenu(); //add logic to make sure user in registered and logged in
                break;
            case "2":
                //login user
                loginMenuPostgres();
                homeMenu(); //same a case "1"
                break;
            case "3":
                int id = 11;
                User u = us.getUserById(id);
                boolean employee = u.isEmployee();
                System.out.println("Is user " + u.getUsername() + " an employee? " + employee);
                break;
            case "4":
                System.out.println("Are you sure you want to quit?(y/n)"); //add logic
                System.exit(0);
            default:
                System.out.println("Sorry, I did not recognize that option\n" +
                        "Try again? (y/n)");
                String resp = sc.nextLine();

                if (resp.equalsIgnoreCase("y")) {
                    startMenu();
                }
                if (resp.equalsIgnoreCase("n")) {
                    sc.close();
                    System.exit(0);
                }
        }
    }

    public static void homeMenu() {
        boolean running = true;
        while (running) {
            System.out.println("What do you want to do?\n" +
                    "1: View Store,\n" +
                    "2: Insert item. (Employee only)\n" +
                    "3: Change availability of an item. (Employee only)\n" +
                    "4: Change price of an item. (Employee only)\n" +
                    "5: Remove item. (Employee only)\n" +
                    "6: Logout\n" +
                    "7: Exit");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    List<Item> itemList = itemService.getAllItems();
                    System.out.println("Here are all the available items:");
                    for (Item i : itemList) {
                        System.out.print("Name: " + i.getName() + " Price: " + i.getPrice() + "\n");
                    }
                    break;

                case "2":
                    if (u.isEmployee()) {
                        System.out.println("Give the name of the item.");
                        String name = sc.nextLine();
                        System.out.println("Give the price of the item.");
                        float price = sc.nextFloat();
                        sc.nextLine();
                        System.out.println("Is the item available? Type true or false.");
                        boolean avail = sc.nextBoolean();
                        sc.nextLine();
                        Item i = new Item();
                        i.setName(name);
                        i.setPrice(price);
                        i.setAvailable(avail);
                        itemService.insertItem(i);
                        break;
                    } else {
                        System.out.println("This option is for employees only.");
                        break;
                    }

                case "3":
                    if (u.isEmployee()) {
                        System.out.println("Give the name of the item.");
                        String name = sc.nextLine();
                        System.out.println("What will the availability be? Type true or false.");
                        boolean avail = sc.nextBoolean();
                        sc.nextLine();
                        Item i = itemService.getByItemName(name);
                        itemService.changeAvailability(i, avail);
                        break;
                    } else {
                        System.out.println("This option is for employees only.");
                        break;
                    }

                case "4":
                    if (u.isEmployee()) {
                        System.out.println("Give the name of the item.");
                        String name = sc.nextLine();
                        System.out.println("What is the new price?");
                        float price = sc.nextFloat();
                        sc.nextLine();
                        Item i = itemService.getByItemName(name);
                        itemService.changePrice(i, price);
                        break;
                    } else {
                        System.out.println("This option is for employees only.");
                        break;
                    }

                case "5":
                    if (u.isEmployee()) {
                        System.out.println("Give the name of the item.");
                        String name = sc.nextLine();
                        Item i = new Item();
                        i.setName(name);
                        itemService.removeItem(i);
                        break;
                    } else {
                        System.out.println("This option is for employees only.");
                        break;
                    }

                case"6":
                    System.out.println("Are you sure you want to logout?(y/n)"); //add logic
                    main(null);
                case "7":
                    System.out.println("Closing program");
                    System.exit(0);
                default:
                    System.out.println("Sorry, didn't understand that.");
            }
        }
    }

    public static void registerMenu() {
        User newUser = new User();
        System.out.println("Register:");
        System.out.println("Please enter your username:");
        newUser.setUsername(sc.nextLine());
        System.out.println("Please enter your password:");
        newUser.setPassword(sc.nextLine());
        u = us.createUser(newUser);
    }


    public static void loginMenuPostgres() {
        User unverifiedUser = new User();
        System.out.println("Login:");
        System.out.println("Please enter your username:");
        unverifiedUser.setUsername(sc.nextLine());
        System.out.println("Please enter your password:");
        unverifiedUser.setPassword(sc.nextLine());
        if (us.getUser(unverifiedUser) != null) {
            u = us.getUser(unverifiedUser);
            System.out.println("Welcome Back, " + unverifiedUser.getUsername() + "!");
        } else {
            System.out.println("Sorry. We could not find a user with those credentials.\n" +
                    "Try again? (y/n)");
            String resp = sc.nextLine();
            resp = resp.toLowerCase();
            if (resp.equals("y")) {
                loginMenuPostgres();
            } else if (resp.equals("n")) {
                System.out.println("Closing program.");
                System.exit(0);
            } else {
                System.out.println("Command not recognized.\n" +
                        "Exiting program.");
            }
        }
    }
}