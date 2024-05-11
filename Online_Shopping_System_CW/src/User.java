import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User {

    private String username;
    private String password;
    private ArrayList<User> userList = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void menu() {
        int choice = 0;
        do {
            System.out.println("""
                    -------------------------------------------------
                    Welcome to Westminster Online System:
                    Please Sign in first  and then login using the relevant credentials.
                        1)Signup
                        2)Login
                            0)Quit
                    -------------------------------------------------        
                    """);
            try {
                System.out.print("Enter Option: ");
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Option");
            }
            switch (choice) {
                case 0 -> System.exit(choice);
                case 1 -> signUp();
                case 2 -> login();
                default -> System.out.println("Invalid choice! Enter a choice between 0 and 2");
            }
        } while (choice != 0);
    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String login_username = sc.next();

        User currentUser = findUsername(login_username, "Username Found!");
        if (currentUser != null) {
            while (true) {
                System.out.print("Enter Password for this username: ");
                String login_password = sc.next();

                if (login_password.equals(currentUser.getPassword())) {
                    System.out.println("Login successful!");
                    break;
                } else {
                    System.out.println("Incorrect password");
                }
            }

        } else {
            System.out.println("Username not found. Login failed.SignUp first");
        }
        openGUI();
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void signUp() {
        Scanner sc = new Scanner(System.in);
        String new_username;
        String new_password;
        User currentUser;

        do {
            System.out.print("Enter new Username: ");
            new_username = sc.next();
            currentUser = findUsername(new_username, "Username Already Exists!");
            if (currentUser == null) {
                break;
            }
        } while (currentUser != null);

        do {
            System.out.print("Enter new Password(6 characters at least): ");
            new_password = sc.next();
            if (validPassword(new_password)) {
                break;
            }
        }
        while (!validPassword(new_password));

        User newUser = new User(new_username, new_password);

        userList.add(newUser);
        System.out.println("SignUp successful");

        openGUI();

    }

    public boolean validPassword(String password) {
        return password.length() >= 6;
    }

    private User findUsername(String username, String message) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                System.out.println(message);
                return user;
            }
        }
        return null;
    }
    private void openGUI(){
        GUI gui=new GUI();
        gui.display();
    }
}




