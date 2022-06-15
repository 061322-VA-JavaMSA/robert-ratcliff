
import java.util.Scanner;

public class Login {

    static Scanner sc = new Scanner(System.in);
    static boolean userConn = true;
    static boolean loggedIn = false;

    public static void main(String[] args){

        menu();
        //close scanner when done
        sc.close();
    }

    public static void loginScreen() {
        String username = "admin";
        String password = "pass";

        System.out.println("Please enter your username");
        String usernameInput = sc.nextLine();
        System.out.println("Please enter your password");
        String passwordInput = sc.nextLine();

        if(username.equals(usernameInput) && password.equals(passwordInput)) {
            System.out.println("Welcome!");
            loggedIn = true;
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    //TODO Functionality
    public static int getRandom(){

        return 0;
    }

    //TODO Error handling.
    public static String reverseString(){
        System.out.println("Type the String you want to reverse.");

        //does not verify if String
        StringBuilder sb = new StringBuilder(sc.nextLine());
        sb.reverse();
        return sb.toString();
    }
    public static void menu() {
        /*
         * Menu that gives a user multiple options:
         * 	- 1: get a random number
         *  - 2: reverse a String of the user's choice
         *  - 3: exit the program
         *
         *  this menu should repeat until the user decides to exit
         */
        loginScreen();
        while(userConn){
            if(loggedIn){
                System.out.println("Would you like to: get a random number type 'random', " +
                        "reverse a String type 'string', or to quit type 'exit'.");
                String resp = sc.nextLine();
                if(resp.equals("random")){
                    int n = getRandom();
                    System.out.println("Your random number is: " + n);
                }else if(resp.equals("string")){
                    String s = reverseString();
                    System.out.println("Your reversed string is: " + s);
                }else{ //TODO Same as below todo.
                    userConn = false;
                }


            }else{
                System.out.println("type 'retry' to reenter credentials or type 'exit' to quit.");
                String resp = sc.nextLine();
                if(resp.equals("retry")){
                    loginScreen();
                }else{ //TODO handle possibility of incorrect keyword being used instead of immediately logging out.
                    userConn = false;
                }
            }
        }
    }
}
