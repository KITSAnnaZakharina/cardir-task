package model;

public interface VendingSupplyFunctions {

    /**
     * Admin Function - This is called when an admin adds new product shelf to the system.
     * Note: Only one product will be added at a time.
     */
    void addNewProduct(int productPosition, String productName, int priceInCent, int productQuantity);

    /**
     * Admin Function - This is called when an admin updates existing product shelf in the system
     */
    void refillExistingProduct(int productPosition, int productQuantity);

}
