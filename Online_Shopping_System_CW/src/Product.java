import java.io.Serializable;

    public abstract class Product implements Serializable {
        private String productID;
        private String productName;
        private int availableItems;
        private int price;
        private String productCategory;

        public Product(String productID, String productName, int availableItems, int price) {
            this.productID = productID;
            this.productName = productName;
            this.availableItems = availableItems;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "productID='" + productID + '\'' +
                    ", productName='" + productName + '\'' +
                    ", availableItems=" + availableItems +
                    ", price=" + price +
                    '}';
        }
        public void productInfo(){
            System.out.println(
                    "Product ID: "+getProductID()+"\n"+
                            "Product Name: "+getProductName()+"\n"+
                            "Available Items: "+getAvailableItems()+"\n"+
                            "Price: $"+getPrice());
        }
        public abstract String getCategoryInfo();
        public String getProductID() {
            return productID;
        }
        public String getProductCategory() {
            return productCategory;
        }
        public void setProductCategory(String productCategory) {
            this.productCategory = productCategory;
        }

        public void setAvailableItems(int availableItems) {
            this.availableItems = availableItems;
        }

        public String getProductName() {return productName;}
        public int getAvailableItems() {
            return availableItems;
        }
        public double getPrice() {
            return price;
        }

    }


