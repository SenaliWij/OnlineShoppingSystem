import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    private JPanel middlePanel;
    private productTableModel model;
    JFrame frame;
    JTable table;
    JComboBox<String> comboBox;
    private ShoppingCart cartFrame;
    private ProductDetails productDetails;

    public GUI() {
        frame = new JFrame("Online shopping system");
        frame.setSize(900, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //TOP PANEL--> (Label,ComboBox,ShoppingCart Button)//
        JPanel topPanel = new JPanel();

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 50));
        panel1.add(new JLabel("Select Product Category:"));

        String[] category = {"All", "Electronic", "Clothing"};
        comboBox = new JComboBox<>(category);
        comboBox.setPreferredSize(new Dimension(130, 30));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        //ActionListener for filtering products according to category
                if (e.getSource() == comboBox) {
                    filterByCategory();
                }
            }
        });
        panel1.add(comboBox);
        panel1.setPreferredSize(new Dimension(550, 120));


        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        JButton shoppingButton = (new JButton("Shopping Cart"));
        cartFrame = new ShoppingCart();
        shoppingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {      //ActionListener so when the shopping cart button is clicked,
                cartFrame.setVisible(true);                   //cartFrame opens
                cartFrame.revalidate();

            }
        });
        panel2.add(shoppingButton);
        panel2.setPreferredSize(new Dimension(200, 120));


        topPanel.add(panel1, new FlowLayout(FlowLayout.LEFT));
        topPanel.add(panel2, new FlowLayout(FlowLayout.RIGHT));

        //TABLE//
        middlePanel = new JPanel();

        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();     //instance of manager created
        ArrayList<Product> products = shoppingManager.loadFile();                      //accessing the saved products using loadFile method
        model = new productTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);


        //DESIGNING THE TABLE//
        table.setRowHeight(30);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.setPreferredSize(new Dimension(800, 250));

        JTableHeader header = table.getTableHeader();                        //designing column headers
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setPreferredSize(new Dimension(header.getWidth(), 36));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);              //Centering the contents of each cell using renderer
        for (int i = 0; i < table.getColumnCount(); i++) {                 //Using for loop to apply it to all columns
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane.setBorder(new EmptyBorder(0, 20, 5, 20));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        //ADDITIONAL TABLE FUNCTIONS//

        //LSelectionLister to select a row and display the product details of the selected product
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1){
                    String selectedID = table.getValueAt(selectedRow, 0).toString();  //accessing productID of the product in the selected row

                    for (Product product : products) {
                        if(product.getProductID().equals(selectedID)){        //goes through arraylist anf find the product w the selectedID
                            productDetails.setDetails(product);               //setDetails method is called to display the product details
                        }
                    }
                }
            }
        });

        //Sorting Columns Alphabetically//
        TableRowSorter sorter = new TableRowSorter<>(model);         //sorter used to sort each column alphabetically
        table.setRowSorter(sorter);

        //Highlighting rows that have 3 or less than available Items in RED//
        DefaultTableCellRenderer redRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);

                Product product = model.getProductAtRow(row);   // Access product information using getProductAtRow method created in the productTableModel class

                if (product != null) {
                    if (product.getAvailableItems() <= 3) {    //checks if available items is less than or equal to 3
                        component.setBackground(Color.RED);    //sets row color to red
                    } else if (!isSelected) {
                        component.setBackground(table.getBackground());
                    }
                }
                return component;
            }
        };


        for (int i = 0; i < table.getColumnCount(); i++) {        // Set the renderer for all columns using for loop
            table.getColumnModel().getColumn(i).setCellRenderer(redRenderer);
        }
        middlePanel.add(scrollPane);

        //BOTTOM PANEL-->(Selected Product Details)

        productDetails=new ProductDetails(cartFrame);  //instance of Product Details created and assigned to display product details

        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(scrollPane,BorderLayout.CENTER);
        frame.add(productDetails,BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    //method to filter products according to category using comboBox
    private void filterByCategory() {
        TableRowSorter<productTableModel> sorter = (TableRowSorter<productTableModel>) table.getRowSorter();
        String chosenCategory = (String) comboBox.getSelectedItem();
        if (chosenCategory.equals("All")) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(chosenCategory, 2));

        }
    }
    public void display() {
        frame.setVisible(true);
    }
    
}

