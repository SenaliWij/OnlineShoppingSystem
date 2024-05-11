import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart extends JFrame {
    private DefaultTableModel cartModel;
    private JTable cartTable;
    private HashMap<Product, Integer> shoppingCartItems = new HashMap<>();
    HashMap<String,Integer> categoryCount=new HashMap<>();
    private double Total;
    private JLabel totalLabel;
    private JLabel discount10Label;
    private JLabel discount20Label;
    private JLabel finalPriceLabel;
    private JPanel checkoutPanel;
    private static DecimalFormat df = new DecimalFormat("0.00");;

    public ShoppingCart()  {      //constructor for shopping cart
        super("Shopping Cart");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        setResizable(false);
        setLayout(new GridLayout(2,0));

        cartModel = new DefaultTableModel();     //Initializing table model
        cartModel.addColumn("Product Details");
        cartModel.addColumn("Quantity");
        cartModel.addColumn("Price");

        cartTable = new JTable(cartModel);      //Initializing table to display Shopping Cart
        JScrollPane scrollPane = new JScrollPane(cartTable);

        //Designing the cart Table//
        JTableHeader header = cartTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setPreferredSize(new Dimension(header.getWidth(), 36));

        scrollPane.setBorder( new EmptyBorder(50, 20, 0, 20));
        cartTable.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < cartTable.getColumnCount(); i++) {
            cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        add(scrollPane);

        // Creating checkout Panel//
        checkoutPanel=new JPanel(new GridLayout(5,0,1,1));
        EmptyBorder border = new EmptyBorder(0, 0, 0, 30);
        checkoutPanel.setBorder(border);
        add(checkoutPanel);
        printCheckout();       //print checkout method calling to display the prices and discounts
    }
    //Method to add a product to the shopping cart
    public void addToCart(Product product) {
        if (shoppingCartItems.containsKey(product)) {       //using hashmap to check if product is already there in the cart
            int quantity = shoppingCartItems.get(product) + 1;   //quantity updated
            shoppingCartItems.put(product, quantity);
        } else {
            shoppingCartItems.put(product, 1);            //or else sets quantity to 1
        }
        updateCartTable();
    }
    //Method to update the contents of the cart table//
    public void updateCartTable() {
            cartModel.setRowCount(0); // Clear the table
            Total=0;
            boolean hasCategoryDiscount=false;
            for (Map.Entry<Product, Integer> entry : shoppingCartItems.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double price = calculatePrice(product.getPrice(),quantity);       //calculatePrice() method called
                Total+=price;

                // Updates the count of items for each category in the shopping cart
                String category = product.getProductCategory();
                int currentCount = categoryCount.getOrDefault(category, 0);
                categoryCount.put(category, currentCount + quantity);
                if (currentCount >= 3) {               // Checks for category discount eligibility within the loop
                    hasCategoryDiscount = true;
                }

                Object rowData[]= {getProductDetails(product),         //array of rowData created and added
                        quantity,
                        price
                };
                cartModel.addRow(rowData);

                totalLabel.setText("Total:  $" + getTotal());;
                discount10Label.setText(" First Purchase Discount(10%):  $"+df.format(firstPurchaseDiscount()));
                discount20Label.setText("Three Items in same Category Discount(20%):  $"+df.format(hasCategoryDiscount ? sameCategoryDiscount() : 0));
                finalPriceLabel.setText(" Final Price:  $"+ df.format(finalPrice()));
            }
    }
    //Method to display checkout prices and discounts on the panel//
    public void printCheckout() {

        //Labels created to display prices and discounts
        totalLabel = new JLabel("Total: ");
        totalLabel.setFont(new Font("Courier", Font.PLAIN, 13));
        discount10Label = new JLabel("First Purchase Discount(10%): ");
        discount10Label.setFont(new Font("Courier", Font.PLAIN, 13));
        discount20Label = new JLabel("Three Items in same Category Discount(20%): ");
        discount20Label.setFont(new Font("Courier", Font.PLAIN, 13));
        finalPriceLabel = new JLabel("Final Price ");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        discount10Label.setHorizontalAlignment(SwingConstants.RIGHT);
        discount20Label.setHorizontalAlignment(SwingConstants.RIGHT);
        finalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        checkoutPanel.add(totalLabel);
        checkoutPanel.add(discount10Label);
        checkoutPanel.add(discount20Label);
        checkoutPanel.add(finalPriceLabel);
    }
    private String getProductDetails(Product product) {
        return  "" +
                product.getProductName()+" [ "+
                "ID: "+product.getProductID()+" "+
                product.getCategoryInfo()+"]";
    }
    private double calculatePrice(double Price, int quantity) {
        return Price * quantity;
    }
    public double getTotal(){
        return Total;
    }
    public double firstPurchaseDiscount(){
        return Total*0.1*-1;
    }
    public double sameCategoryDiscount(){

        boolean threeItems=false;
        for (int count : categoryCount.values()) {
            if(count>=4){
                threeItems=true;
                break;
            }
        }
        if(threeItems){
            return Total*0.2*-1;
        }
        else {
            return 0;
        }
    }
    public double finalPrice(){
        double total=getTotal();
        double discount10=firstPurchaseDiscount();
        double discount20=sameCategoryDiscount();
        double finalPrice=total+discount10+discount20;
        return finalPrice;
    }
}

