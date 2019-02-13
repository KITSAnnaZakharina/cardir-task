package model;

/**
 * This class contains information about product shelf.
 * All products on the shelf are the same type and same price.
 */
public class ProductShelf {

    private String productName;
    private Integer productPrice;
    private int productQuantity;

    Integer getProductPrice() {
        return productPrice;
    }

    String getProductName() {
        return productName;
    }

    int getProductQuantity() {
        return productQuantity;
    }

    void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    ProductShelf(String name, Integer price, int quantity) {
        this.productName = name;
        this.productPrice = price;
        this.productQuantity = quantity;
    }


}
