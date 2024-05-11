import java.io.Serializable;


    public class Clothing extends Product implements Serializable {
        private String size;
        private String color;

        public Clothing(String productID, String productName, int availableItems, int price, String size, String color) {
            super(productID, productName, availableItems, price);
            this.size = size;
            this.color = color;
            setProductCategory("Clothing");
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Clothing{" +
                    "size=" + size +
                    ", color='" + color + '\'' +
                    "} " + super.toString();
        }
        @Override
        public void productInfo() {
            super.productInfo();
            System.out.println("Size: "+getSize()+"\n"+
                    "Color: "+getColor());
        }

        @Override
        public String getCategoryInfo() {
            return "Size: "+getSize()+"\n"
                    +" ,Color: "+ getColor();
        }
    }


