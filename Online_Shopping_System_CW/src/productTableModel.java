import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class productTableModel extends AbstractTableModel {
    WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
    ArrayList<Product> productTable = shoppingManager.loadFile();

    String[] columnNames = {"ProductID", "Name", "Category", "Price($)", "Info"};
    public productTableModel() { // Remove the parameter
        this.productTable = shoppingManager.loadFile();
    }

    @Override
    public int getRowCount() {
        return productTable.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = productTable.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return product.getProductID();
            case 1:
                return product.getProductName();
            case 2:
                return product.getProductCategory();
            case 3:
                return product.getPrice();
            case 4:
                return product.getCategoryInfo();
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    public Product getProductAtRow(int row) {
        return productTable.get(row);
    }
}

