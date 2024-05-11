import java.io.*;
import java.util.*;

    public class WestminsterShoppingManager implements ShoppingManager, Serializable {
        ArrayList<Product> products;       //Arraylist for products
        public WestminsterShoppingManager() {

            this.products = loadFile();    //The returned arraylist in the load method is assigned here to products arrayList
        }
        public void menu() {    //menu to carry out these options
            int choice;
            do {
                String dashes = "--------------------------------------------------------------------------------------";
                printActions();
                choice = int_validation("\n Enter Action: ", "Invalid! Enter a choice between 0 and 4");
                System.out.println(dashes);
                switch (choice) {
                    case 0 -> System.exit(choice);
                    case 1 -> addProducts();
                    case 2 -> removeProduct();
                    case 3 -> printProducts();
                    case 4 -> saveFile(products);
                    default -> System.out.println("Invalid choice! Enter a choice between 0 and 4");
                }
            } while (choice != 0);
        }

        // method to display available actions//
        private static void printActions() {
            String textBlock = """
                --------------------------------------------------------------------------------------
                Available actions:
                        
                0 - Quit
                1 - Add a new product
                2 - Delete a product
                3 - Print the list of the products
                4 - Save in a file
          
              --------------------------------------------------------------------------------------""";
            System.out.print(textBlock);
        }
        //method to add both clothing and electronic products//
        public void addProducts() {

            Scanner sc = new Scanner(System.in);
            while (true) {
                if (products.size() < 50) {
                    System.out.print("Enter Category of product(Electronic/Clothing): ");
                    String productCategory = sc.next();
                    if (productCategory.equalsIgnoreCase("Electronic")) {
                        products.add(addElectronics());    //returned electronic product added to ArrayList
                    } else if (productCategory.equalsIgnoreCase("Clothing")) {
                        products.add(addClothes());        //returned clothing product added to ArrayList
                    } else {
                        System.out.println("Invalid Category");
                    }
                } else {
                    System.out.println("Number of products reached.Cannot add more new products");
                }
                System.out.print("\nDo you want to add another product?(Y/N): ");
                String another_product = sc.next();
                if (!another_product.equalsIgnoreCase("y")) {
                 break;
                }
            }
        }
        // general int validation method to validate int inputs//
        public int int_validation(String input, String message) {   //parameters are the input and the error message to be displayed w each input
            Scanner sc = new Scanner(System.in);
            while (true) {
                try {
                    System.out.print(input);
                    int var = sc.nextInt();
                    if (var >= 0) {
                        return var;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(message);
                    sc.nextLine();
                }
            }

        }
        // method to add electronic products//
        Electronic addElectronics() {
            Scanner sc = new Scanner(System.in);

            String productID;
            while(true) {
                System.out.print("Enter ProductID: ");
                productID = sc.next();
                if(Character.isLetter(productID.charAt(0))) {
                    if (!duplicateProductID(productID)) {
                       break;
                    }
                    else{
                        System.out.println("ProductID already exists.Please try a different ID");
                    }
                }
                else{
                    System.out.println("Product ID should start with a Letter");
                }
            }
            System.out.print("Enter Product Name: ");
            String productName = sc.next();
            int availableItems = int_validation("Enter Available number of Items: ","Invalid! Enter non-negative Integer.");
            int price = int_validation("Enter Price of Item: ","Invalid! Enter non-negative Numerical Value.");
            System.out.print("Enter Brand: ");
            String brand = sc.next();
            int warrantyPeriod = int_validation("Enter Warranty Period: ","Invalid! Enter non-negative Integer. .");
            return new Electronic(productID, productName, availableItems, price, brand, warrantyPeriod);             //electronic object created and returned//
        }
        //method to add clothing products//

        private Clothing addClothes() {
            Scanner sc = new Scanner(System.in);
            String productID;
            while(true) {                                   //ProductID is validated here//
                System.out.print("Enter ProductID: ");
                productID = sc.next();
                if(Character.isLetter(productID.charAt(0))) {        //Checks if the first letter of the ProductID is a letter
                    if (!duplicateProductID(productID)) {            //Checks if the productID already exists
                        break;
                    }
                    else{
                        System.out.println("ProductID already exists.Please try a different ID");
                    }
                }
                else{
                    System.out.println("Product ID should start with a Letter");
                }
            }

            System.out.print("Enter Product Name: ");
            String productName = sc.next();
            int availableItems = int_validation("Enter Available number of Items: ","Invalid! Enter non-negative Integer.");
            int price = int_validation("Enter Price of Item: ","Invalid! Enter non-negative Numerical Value.");
            System.out.print("Enter Size: ");
            String size = sc.next();
            System.out.print("Enter Color: ");
            String color = sc.next();
            return new Clothing(productID, productName, availableItems, price, size, color);     //Clothing project created and
        }
        //method to check if the product ID already exists(This keeps it unique)//
        private boolean duplicateProductID(String productID) {
            for (Product product : products) {
                if (product.getProductID().equals(productID)) {
                    return true;
                }
            }
            return false;
        }
        //method to remove products using productID//
        public void removeProduct() {
            while (true) {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter ProductID of the product you wanna remove: ");
                String removeProductID = sc.next();
                boolean productFlag=false;          //flag to see if the productID exists or not
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    if (product.getProductID().equals(removeProductID)) {
                        productFlag=true;
                        products.remove(product);  // Remove using the index
                        System.out.println("Product removed successfully! ");

                        System.out.println("Removed Product Information:"+"\n");
                        product.productInfo(); //Printing removed product information by calling the printInfo() method.
                        break;
                    }
                }
                if(!productFlag){
                    System.out.println("Product ID not found!");
                }
                System.out.print("\nDo you want to delete another product?(Y/N): ");
                String another_product = sc.next();
                if (!another_product.equalsIgnoreCase("y")) {
                    break;
                }
            }
        }

        public void printProducts() {
            Comparator<Product> productComparator = Comparator.comparing(Product::getProductID);
            Collections.sort(products, productComparator);
            int prodCount = 1;
            for (Product product : products) {
                System.out.println("\n" + "Product " + prodCount++);
                System.out.println("Product Category: "+ product.getProductCategory());
                product.productInfo();
            }
        }
        //method to save the products in the arraylist to a file//
        public void saveFile(ArrayList<Product> products) {
            try {
                FileOutputStream fos = new FileOutputStream("productList.txt");   //file created
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(products);
                oos.close();

                System.out.println("Successfully saved to file!");
            } catch (IOException e) {
                System.out.println("Error has occurred!");
            }
        }
        //method to load the products from the file//
        public ArrayList<Product> loadFile() {

            try {
                FileInputStream fis = new FileInputStream("productList.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                products= (ArrayList<Product>) ois.readObject();
                ois.close();
                System.out.println(products.toString());
                System.out.println("Successfully loaded from file!");

            } catch (IOException | ClassNotFoundException e) {
//                System.out.println("Error has occurred! ");
                products= new ArrayList<>();      //returns empty arraylist if no products arrayList is null
            }
            return products;
        }
    }










