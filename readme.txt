
This project contains implementation of Vending Machine based on provided code
   (VendingMachine and VendingMachineHardwareFunctions interfaces).

Code contains unit test coverage based on JUnit. Tests are located in src\test\java\model.CarDirectMachineTest class.
Example of usage described in the class model.CarDirectMachine.Run
There are several rules how the CarDirectMachine works:
    1. CarDirectMachine does not calculate sold products and earned money.
    2. CarDirectMachine does not have 'return coins' button.
    3. After a product purchase the change will be returned automatically.
    4. If user added money and then choose product with price more that user added to the account balance,
       then the balance will be returned automatically.
