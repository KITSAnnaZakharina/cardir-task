package model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CarDirectMachineTest {
    static ByteArrayOutputStream baos;

    /**
     * Returns message from console (Vending Machine) base on index number starting from the end
     * @param countFromEnd index number
     * @return message from console
     */
    private static String getLastMsg(int countFromEnd) {
        String[] msgs = baos.toString().split("\r\n");
        return msgs[msgs.length - countFromEnd];
    }

    /**
     * Collects all messages from console (Vending Machine)
     */
    @BeforeClass
    public static void setUp() {
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMachineNegaviteShelfCapacityFail() {
        int shelfCapacity = -1;
        new CarDirectMachine(10, shelfCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMachineZeroShelfCapacityFail() {
        int shelfCapacity = 0;
        new CarDirectMachine(10, shelfCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMachineNegaviteShelfQtyFail() {
        int maxShelves = -1;
        new CarDirectMachine(maxShelves, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMachineZeroShelfQtyFail() {
        int maxShelves = 0;
        new CarDirectMachine(maxShelves, 10);
    }

    @Test
    public void testAddNewProductSuccess() {
        new CarDirectMachine(10, 10).addNewProduct(1, "Lays", 80, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewProductWithRegisteredShelfIdFail() {
        int productId = 1;
        int shelfCapacity = 10;
        CarDirectMachine testMachine = new CarDirectMachine(10, shelfCapacity);
        testMachine.addNewProduct(productId, "Mars", 200, --shelfCapacity);
        testMachine.addNewProduct(productId, "Mars", 200, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewProductQuantityMoreThanShelfCapacityFail() {
        int shelfCapacity = 10;
        new CarDirectMachine(20, shelfCapacity).addNewProduct(1, "Mars", 200, ++shelfCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewProductNoFreeShelfInMachineFail() {
        int maxShelves = 1;
        int productId = 1;
        CarDirectMachine testMachine = new CarDirectMachine(maxShelves, 10);
        testMachine.addNewProduct(productId, "Mars", 200, 1);
        testMachine.addNewProduct(productId, "Mars", 200, 1);
    }

    @Test
    public void testRefillProductSuccess() {
        int shelfCapacity = 10;
        int productId = 1;
        CarDirectMachine testMachine = new CarDirectMachine(10, shelfCapacity);
        testMachine.addNewProduct(productId, "Mars", 200, --shelfCapacity);
        testMachine.refillExistingProduct(productId, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRefillProductNoFreeSlotsOnShelfFail() {
        int capacity = 10;
        int productId = 1;
        CarDirectMachine testMachine = new CarDirectMachine(10, capacity);
        testMachine.addNewProduct(productId, "Mars", 200, capacity);
        testMachine.refillExistingProduct(productId, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRefillProductNoShelfIdFail() {
        new CarDirectMachine(10, 10).refillExistingProduct(1, 1);
    }

    @Test
    public void testButtonPressShowPriceSuccess() {
        CarDirectMachine testMachine = new CarDirectMachine(10, 10);
        testMachine.addNewProduct(3, "Mars", 200, 1);
        testMachine.buttonPress(3);
        Assert.assertEquals("Selected product Mars $2.00", getLastMsg(1));
    }

    @Test
    public void testButtonPressGiveChangePurchaseSuccess() {
        CarDirectMachine testMachine = new CarDirectMachine(10, 10);
        testMachine.addNewProduct(1, "Bounty", 15, 5);
        testMachine.addUserMoney(25);
        testMachine.buttonPress(1);
        Assert.assertEquals("Dispensing Bounty from position 1", getLastMsg(2));
        Assert.assertEquals("Dispensing 10 cents", getLastMsg(1));
    }

    @Test
    public void testButtonPressGiveChangePurchaseFail() {
        CarDirectMachine testMachine = new CarDirectMachine(10, 10);
        testMachine.addNewProduct(1, "Bounty", 45, 5);
        testMachine.addUserMoney(25);
        testMachine.buttonPress(1);
        Assert.assertEquals("Not enough balance to purchase selected product. Your balance is $0.25", getLastMsg(2));
        Assert.assertEquals("Dispensing 25 cents", getLastMsg(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testButtonPressNegativeValueFail() {
        new CarDirectMachine(10, 10).buttonPress(-1);
    }

    @Test
    public void testAddUserMoneySuccess() {
        CarDirectMachine testMachine = new CarDirectMachine(10, 10);
        testMachine.addUserMoney(25);
        Assert.assertEquals("Your balance is $0.25", getLastMsg(1));
        testMachine.addUserMoney(5);
        Assert.assertEquals("Your balance is $0.30", getLastMsg(1));
    }

    @Test
    public void testAddUserMoneyFail() {
        CarDirectMachine testMachine = new CarDirectMachine(10, 10);
        testMachine.addUserMoney(35);
        Assert.assertEquals("Payment not accepted", getLastMsg(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserMoneyNegativeValueFail() {
        new CarDirectMachine(10, 10).addUserMoney(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserMoneyZeroFail() {
        new CarDirectMachine(10, 10).addUserMoney(0);
    }


}
