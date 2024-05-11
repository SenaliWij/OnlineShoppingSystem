import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    public static void main(String[] args) {
        WestminsterShoppingManager manager=new WestminsterShoppingManager();
        User user=new User("","");
        int choice = 0;
        do {
            System.out.println("""
                -------------------------------------------------
                Are you a Customer or a Manager:
                    1)Customer
                    2)Manger
                        0)Quit
                -------------------------------------------------
                """);

            try{
                System.out.print("Enter Option: ");
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid Option! ");
                System.out.print("Enter Option: ");
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();

            }
            switch (choice) {
                case 0 -> System.exit(choice);
                case 1 -> user.menu();
                case 2 -> manager.menu();
                default -> System.out.println("Invalid option! Enter a option between 0 and 2");
            }
        } while (choice != 0);

    }
}