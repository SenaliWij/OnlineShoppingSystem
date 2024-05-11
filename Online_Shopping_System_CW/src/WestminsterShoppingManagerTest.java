import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {
    @Test
    public void testAddProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        // Test adding electronics
        ByteArrayInputStream in = new ByteArrayInputStream(
                ("ABC123\nPhone\n10\n500\nApple\n12\nN\n").getBytes()); 
        System.setIn(in);
        InputStream originalIn = System.in; // Store original input stream

        manager.addProducts();

        System.setIn(originalIn); // Restore original input stream

        assertEquals(1, manager.products.size());
        Electronic product = (Electronic) manager.products.get(0);
        assertEquals("ABC123", product.getProductID());
        assertEquals("Phone", product.getProductName());
        assertEquals(10, product.getAvailableItems());
        assertEquals(500, product.getPrice());
        assertEquals("Apple", product.getBrand());
        assertEquals(12, product.getWarrantyPeriod());

        // Test adding clothes
        in = new ByteArrayInputStream(
                ("XYZ456\nShirt\n5\n25\nM\nRed\nN\n").getBytes()); // Product details, then 'N' to exit
        System.setIn(in);

        manager.addProducts();

        System.setIn(originalIn); // Restore original input stream

        assertEquals(2, manager.products.size());
        Clothing clothing = (Clothing) manager.products.get(1);
        assertEquals("XYZ456", clothing.getProductID());
        assertEquals("Shirt", clothing.getProductName());
        assertEquals(5, clothing.getAvailableItems());
        assertEquals(25, clothing.getPrice());
        assertEquals("M", clothing.getSize());
        assertEquals("Red", clothing.getColor());
    }
    @Test
    public void testAddElectronics() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ByteArrayInputStream in = new ByteArrayInputStream(
                ("ABC123\nPhone\n10\n500\nApple\n12\n").getBytes()); // Product details
        System.setIn(in);
        InputStream originalIn = System.in; // Store original input stream

        Electronic product = manager.addElectronics();

        System.setIn(originalIn); // Restore original input stream

        // Assert product properties are set correctly
        assertEquals("ABC123", product.getProductID());
        assertEquals("Phone", product.getProductName());
        assertEquals(10, product.getAvailableItems());
        assertEquals(500, product.getPrice());
        assertEquals("Apple", product.getBrand());
        assertEquals(12, product.getWarrantyPeriod());
    }
    @Test
    public void testRemoveProduct() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.products.add(new Electronic("ABC123", "Phone", 10, 500, "Apple", 12));
        manager.products.add(new Clothing("XYZ456", "Shirt", 5, 25, "M", "Red"));

        ByteArrayInputStream in = new ByteArrayInputStream("ABC123\nN\n".getBytes()); // Product ID, then 'N' to exit
        System.setIn(in);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        manager.removeProduct();

        System.setIn(System.in);
        System.setOut(originalOut);

        // Assert product removed and output is correct
        assertEquals(1, manager.products.size());
        assertTrue(out.toString().contains("Product removed successfully!"));
    }

    @Test
    public void testPrintProducts() {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        manager.products.add(new Electronic("ABC123", "Phone", 10, 500, "Apple", 12));
        manager.products.add(new Clothing("XYZ456", "Shirt", 5, 25, "M", "Red"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        manager.printProducts();

        System.setOut(System.out); // Restore original output stream

        String expectedOutput = """
        ----------------------------
        Product 1
        Product Category: Electronic
        Product ID: ABC123
        Product Name: Phone
        Available Items: 10
        Price: 500
        Brand: Apple
        Warranty Period: 12
        ----------------------------
        Product 2
        Product Category: Clothing
        Product ID: XYZ456
        Product Name: Shirt
        Available Items: 5
        Price: 25
        Size: M
        Color: Red
        ----------------------------
        """;
        assertEquals(expectedOutput, out.toString());
    }
}