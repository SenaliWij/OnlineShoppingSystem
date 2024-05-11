import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ProductDetails extends JPanel {
    private  JPanel productInfoPanel;
    private  JPanel AddToCartPanel;
    private final JLabel productIDLabel;
    private final JLabel categoryLabel;
    private final JLabel nameLabel;
    private final JLabel sizeLabel;
    private final JLabel colorLabel;
    private final JLabel brandLabel;
    private final JLabel warrantyPeriodLabel;
    private final JLabel itemsAvailableLabel;
    private Product selectedProduct;
    private final JLabel heading;


    public ProductDetails(ShoppingCart cart) {

        setLayout(new BorderLayout());
        //productInfo panel//
        productInfoPanel = new JPanel(new GridLayout(7, 0, 9, 7));

        //Labels created to display product details
        heading=new JLabel("Selected Product-Details");
        productIDLabel = new JLabel("Product ID: ");
        categoryLabel = new JLabel("Category: ");
        nameLabel = new JLabel("Name: ");
        sizeLabel = new JLabel("Size: ");
        colorLabel = new JLabel("Color: ");
        brandLabel = new JLabel("Brand: ");
        warrantyPeriodLabel = new JLabel("Warranty Period: ");
        itemsAvailableLabel = new JLabel("Items Available: ");

        // setting the font to PLAIN like in the mock-up using for loop
        JLabel[] labels = {productIDLabel, categoryLabel, nameLabel, sizeLabel, colorLabel, brandLabel, warrantyPeriodLabel, itemsAvailableLabel};

        for (JLabel label : labels) {
            label.setFont(new Font("Courier", Font.PLAIN, 13));
        }

        //AddToCart panel//
        AddToCartPanel = new JPanel();
        JButton cartButton=new JButton("Add to Shopping Cart");
        cartButton.addActionListener(new ActionListener() {       //ActionListener so when  Add to cart button is clicked, the selected product gets added
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == cartButton ) {
                    int availableItems = selectedProduct.getAvailableItems();
                    if (availableItems > 0) {                    //no of available items decremented everytime button is clicked
                        availableItems--;
                        selectedProduct.setAvailableItems(availableItems);
                        itemsAvailableLabel.setText("Items Available: " + availableItems);

                        cart.addToCart(selectedProduct);          //addToCart method called
                    }
                }
            }
        });
        AddToCartPanel.add(cartButton);

        //Layout
        productInfoPanel.setBorder( new EmptyBorder(15, 45, 0, 0));
        AddToCartPanel.setBorder( new EmptyBorder(10, 0, 5, 0));
        add(productInfoPanel,BorderLayout.WEST);
        add(AddToCartPanel,BorderLayout.SOUTH);
    }

    public void setDetails(Product product){

       productInfoPanel.removeAll();
        selectedProduct = product;

        //setting texts for Labels//
        productIDLabel.setText("Product ID: " + selectedProduct.getProductID());
        categoryLabel.setText("Category: " + selectedProduct.getProductCategory());
        nameLabel.setText("Name: " + selectedProduct.getProductName());

        productInfoPanel.add(heading);
        productInfoPanel.add(productIDLabel);
        productInfoPanel.add(categoryLabel);
        productInfoPanel.add(nameLabel);

        if (selectedProduct.getProductCategory().equals("Clothing")) {     //for clothing only brand and warrantyPeriod will be displayed
            sizeLabel.setText("Size: " + ((Clothing) selectedProduct).getSize());
            colorLabel.setText("Color: " +((Clothing) selectedProduct).getColor());
            productInfoPanel.add(sizeLabel);
            productInfoPanel.add(colorLabel);
            sizeLabel.setVisible(true);
            colorLabel.setVisible(true);
            brandLabel.setVisible(false);
            warrantyPeriodLabel.setVisible(false);

        }
        else if (selectedProduct.getProductCategory().equals("Electronic")) {       //for electronics only brand and warrantyPeriod will be displayed
            brandLabel.setText("Brand: " + ((Electronic) selectedProduct).getBrand());
            warrantyPeriodLabel.setText("Warranty Period: " +((Electronic) selectedProduct).getWarrantyPeriod()+" months");
            productInfoPanel.add(brandLabel);
            productInfoPanel.add(warrantyPeriodLabel);
            brandLabel.setVisible(true);
            warrantyPeriodLabel.setVisible(true);
            sizeLabel.setVisible(false);
            colorLabel.setVisible(false);
        }

        itemsAvailableLabel.setText("Items Available: " + selectedProduct.getAvailableItems());
        productInfoPanel.add(itemsAvailableLabel);

        productInfoPanel.revalidate();
        productInfoPanel.repaint();
    }

}

