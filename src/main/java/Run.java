import model.CarDirectMachine;

public class Run {

    public static void main(String... args) {
/** Create new machine */
        CarDirectMachine machine = new CarDirectMachine(20, 10);

/** Use case 3: As an admin, I would like to be able to add more products.
 Workflow:
 1. Admin adds new product to the vending machine
 2. Admin refill existing product in the vending machine */
        machine.addNewProduct(1, "Skittles", 45, 2);
        machine.addNewProduct(3, "Lay's", 80, 7);
        machine.refillExistingProduct(1, 3);

/** Use case 1: As a user, I would like to be able to find the price of a specific product.
 Workflow:
 1. User presses the button corresponding to slot 3 ('model.VendingMachine.buttonPress(3)' is called).
 2. The name and price of that product is displayed. */
        machine.buttonPress(3);

/** Use case 2: As a user, I would like to be able to purchase a product.
 Workflow:
 1. User adds a quarter ('model.VendingMachine.addUserMoney(25)' is called).
 2. User adds a quarter ('model.VendingMachine.addUserMoney(25)' is called).
 3. User presses the button corresponding to slot 1 ('model.VendingMachine.buttonPress(1)' is called).
 *Note: In this example, the product in slot 1 costs 45 cents.
 4. The product from slot 1 and a nickel is dispensed to the user. */
        machine.addUserMoney(25);
        machine.addUserMoney(25);
        machine.buttonPress(1);
    }
}
