import java.io.Serializable;

    public class Electronic extends Product implements Serializable {
        private String Brand;
        private int warrantyPeriod;


        public Electronic(String productID, String productName, int availableItems, int price, String brand, int warrantyPeriod) {
            super(productID, productName, availableItems, price);
            Brand = brand;
            this.warrantyPeriod = warrantyPeriod;
            setProductCategory("Electronic");
        }
        public void setBrand(String brand) {
            Brand = brand;
        }
        public String getBrand() {
            return Brand;
        }
        public int getWarrantyPeriod() {
            return warrantyPeriod;
        }

        @Override
        public String toString() {
            return "Electronic{" +
                    "Brand='" + Brand + '\'' +
                    ", warrantyPeriod=" + warrantyPeriod +
                    "} " + super.toString();
        }

        @Override
        public void productInfo() {
            super.productInfo();
            System.out.println("" +
                    "Electronic Brand: "+getBrand()+"\n"+
                    "Warranty Period(in months): "+getWarrantyPeriod());
        }

        @Override
        public String getCategoryInfo() {
            return "Brand: "+getBrand()
                    +" ,Warranty Period: "+getWarrantyPeriod()+" months";
        }

    }





