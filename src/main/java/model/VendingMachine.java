package model;

interface VendingMachine {

    /**
     * User Function - This is called when a user presses a button for a
     particular product. This is used for both price checking and purchasing.
     */
    void buttonPress(Integer productPosition);

    /**
     * User Function - This is called when the user adds money to the machine.
     * The cents parameter represent the value of the particular currency added to the machine.
     * Note: Only one coin will be added at a time.
     */
    void addUserMoney(Integer cents);
}
