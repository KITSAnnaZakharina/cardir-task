package model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * This class contains implementation of Carrier Direct Vending Machine
 */
public class CarDirectMachine implements VendingMachine, VendingMachineHardwareFunctions, VendingSupplyFunctions {

    final static int[] ACCEPTED_MONEY = {5, 10, 25};
    private final int maxShelves;
    private final int shelfCapacity;
    private Map<Integer, ProductShelf> occupiedShelves;
    private int balance;


    /**
     * Represents all possible messages in the current Vending Machine
     */
    private enum Msg {
        BALANCE("Your balance is "),
        BALANCE_LOW("Not enough balance to purchase selected product. "),
        PRODUCT_SELECTED("Selected product "),
        PRODUCT_CURRENCY("$"),
        PRODUCT_NOT_EXIST("No products for position "),
        PAYMENT_NOT_ACCEPTED("Payment not accepted");

        private String value;

        Msg(String value) {
            this.value = value;
        }
    }

    /**
     * Creates a new Carrier Direct Vending Machine
     *
     * @param maxShelves    This is maximum allowable amount of shelves in the machine
     * @param shelfCapacity This is maximum allowable amount of products on a shelf
     * @throws IllegalArgumentException if maxShelves or shelfCapacity less then one.
     */
    public CarDirectMachine(int maxShelves, int shelfCapacity) {
        if (maxShelves < 1 || shelfCapacity < 1)
            throw new IllegalArgumentException("Parameters maxShelves and shelfCapacity should be more then zero");
        this.maxShelves = maxShelves;
        this.shelfCapacity = shelfCapacity;
        this.occupiedShelves = new HashMap<>(maxShelves);
    }

    /**
     * Transfers price into user friendly form
     *
     * @param price This is price in cents.
     * @return formatted price.
     */
    private String printPrice(int price) {
        return Msg.PRODUCT_CURRENCY.value + String.format(Locale.US, "%.2f", (double) price / 100);
    }

    /**
     * Marks the shelf as occupied by specific product and update it's quantity if the Vending Machine has free shelf
     * Note: Only one product shelf will be added at a time
     *
     * @param productShelfId  This is the id of the product shelf to be occupied by specific product
     * @param productName     This is the name of products on the shelf
     * @param priceInCent     is the price of the product in cents
     * @param productQuantity is the quantity to be added on the shelf.
     * @throws IllegalArgumentException if product shelf with specific id already occupied
     * @throws IllegalArgumentException if product shelf with specific id does not contain enough free slots
     * @throws IllegalArgumentException if the Vending Machine does not contain free shelves
     */
    @Override
    public void addNewProduct(int productShelfId, String productName, int priceInCent, int productQuantity) {
        if (occupiedShelves.containsKey((productShelfId))) {
            throw new IllegalArgumentException("Addition failed: shelf with id " + productShelfId + " already occupied.");
        } else {
            if (occupiedShelves.size() < maxShelves) {
                if (productQuantity <= shelfCapacity) {
                    occupiedShelves.put(productShelfId, new ProductShelf(productName, priceInCent, productQuantity));
                } else
                    throw new IllegalArgumentException("Shelf addition failed: not enough free slots on the shelf with id " + productShelfId);
            } else throw new IllegalArgumentException("Shelf addition failed: no free shelves in the Vending Machine");
        }
    }

    /**
     * Updates existing product shelf in the system if the shelf contains enough free slots to be refilled
     *
     * @param productShelfId  This is the id of the product shelf to be refilled (also known as productPosition)
     * @param productQuantity This is the quantity of products to add for existing specific product shelf
     * @throws IllegalArgumentException if product shelf with specific id does not contain enough free slots
     * @throws IllegalArgumentException if product shelf with specific id not registered with any product
     */
    @Override
    public void refillExistingProduct(int productShelfId, int productQuantity) {
        if (occupiedShelves.containsKey((productShelfId))) {
            ProductShelf shelf = occupiedShelves.get(productShelfId);
            if (shelfCapacity >= shelf.getProductQuantity() + productQuantity) {
                shelf.setProductQuantity(shelf.getProductQuantity() + productQuantity);
            } else
                throw new IllegalArgumentException("Shelf addition failed: not enough free slots on the shelf with id " + productShelfId);
        } else
            throw new IllegalArgumentException("Refill failed: shelf with id " + productShelfId + " does not registered with any product");
    }

    /**
     * This is used for both price checking and purchasing.
     *
     * @param productShelfId This is the id of the product shelf to purchase product or check it's price
     * @throws IllegalArgumentException if productShelfId has negative value
     */
    @Override
    public void buttonPress(Integer productShelfId) {
        if (productShelfId < 0)
            throw new IllegalArgumentException("Input parameter should be positive integer. Actual " + productShelfId);
        if (occupiedShelves.containsKey((productShelfId))) {
            ProductShelf selectedProduct = occupiedShelves.get(productShelfId);
            showMessage(Msg.PRODUCT_SELECTED.value + selectedProduct.getProductName() + " " + printPrice(selectedProduct.getProductPrice()));
            if (balance > 0) {
                if (balance >= selectedProduct.getProductPrice()) {
                    dispenseProduct(productShelfId, selectedProduct.getProductName());
                    balance -= selectedProduct.getProductPrice();
                    if (selectedProduct.getProductQuantity() == 1) occupiedShelves.remove(productShelfId);
                    if (balance > 0) dispenseChange(balance);
                } else {
                    showMessage(Msg.BALANCE_LOW.value + Msg.BALANCE.value + printPrice(balance));
                    dispenseChange(balance);
                }
            }
        } else {
            showMessage(Msg.PRODUCT_NOT_EXIST.value + productShelfId);
            if (balance > 0) dispenseChange(balance);
        }
        balance = 0;
    }

    /**
     * Adds money to the balance. Only Nickels, Dimes and Quarters will be added.
     * Note: Only one coin will be added at a time.
     *
     * @param cents parameter represent the value of the particular currency added to the machine
     * @throws IllegalArgumentException if money value is less than 1
     */
    @Override
    public void addUserMoney(Integer cents) {
        if (cents < 1) throw new IllegalArgumentException("Input parameter should be more then zero. Actual " + cents);
        if (IntStream.of(ACCEPTED_MONEY).anyMatch(x -> x == cents)) {
            balance += cents;
            showMessage(Msg.BALANCE.value + printPrice(balance));
        } else {
            showMessage(Msg.PAYMENT_NOT_ACCEPTED.value);
        }
    }
}
