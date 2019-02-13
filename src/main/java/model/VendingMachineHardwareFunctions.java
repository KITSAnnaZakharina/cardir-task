package model;

/**
 * This interface is intended to abstract the interaction with the vending machine's user interface.
 * <p>
 * Note: These functions do not validate if they function correctly,
 * meaning you can call `dispenseProduct` even if the machine does not contain any products,
 * however you should take care to NOT make a call the cannot be completed.
 * Example: do not call `dispenseProduct` for a product that is out of stock.
 */
interface VendingMachineHardwareFunctions {

    default void showMessage(String message) {
        System.out.println(message);
    }

    default void dispenseProduct(Integer productPosition, String productName) {
        String nullSafeProductName = (productName != null) ? productName : "ProductNum" + productPosition;
        System.out.println("Dispensing " + nullSafeProductName + " from position " + productPosition);
    }

    default void dispenseChange(Integer changeInCents) {
        System.out.println("Dispensing " + changeInCents + " cents");
    }
}
