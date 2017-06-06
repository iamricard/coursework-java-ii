# Submission

## Synopsis

Hand-in document for the part 2 of the Programming Coursework at Ada College.

## Tasks

1. Create a new application named `ManageAccount.java` (similar to
  `Transactions.java`) that uses the `Account` class as follows:

  1. Creates three new `Account`s initialized as specified below:
    * “Barack Obama" with £400 as initial balance and account number: 20230715
    * "Bill Gates" with £500 as initial balance and account number 31558040
    * "Tom Cruise" with £600 as initial balance and account number 44003050

  1. Deposits £50 in the first account, and prints resulting balance
  1. Withdraws £400 from the second account, and prints resulting balance
  1. Deposits £75 in the first account, and prints resulting balance
  1. Prints out Account info for all three accounts

1. Examine the `getBalance()` method in the `Account` class. Note that it
  returns the balance in the account. Add some more code in
  `ManageAccount.java` to use the `getBalance()` method to get the balances of
  the three accounts and add them together to obtain the total amount of
  money in the bank. Print the total and verify that you are getting the right
  amount

1. Modify the `withdraw()` method to print an appropriate message, when there
  is not sufficient fund in the account.

1. Add another version of the `withdraw()` method. This version does NOT charge
  a withdrawal fee, so it has only one parameter. ( Java allows you to define
  alternative versions of methods using the same method name as long as the
  different versions also have different a different number of parameters)

1. Use this version of the method in `ManageAccount.java` to `withdraw` the
  taxes from the accounts. (Reminder: The name of this method should still be
  `withdraw()`. You need to write some additional code in `ManageAccount.java`
  to "tax" the accounts by withdrawing 15% from each of the first three accounts

1. Add another version of the constructor, which takes only 2 parameters:
  `name` and `account number` (i.e., no initial balance). This constructor
  creates an `Account` object with initial balance £0. Modify
  `ManageAccount.java` to use this version of the constructor to create the
  “Inland revenue” account.

1. Add additional code to the `ManageAccount.java` to deposit the total tax to  the “Inland revenue” account.

1. Create a new method that adds interest to the account. The amount added
  should be computed according to the rate given by its parameter. For example,
  if the `acct1` balance is £100.00 and the method is invoked as follows:
  `acct1.addInterest(0.015);` the balance of `acct1` should increase by 1.5%
  (so £100 + £1.50 = £101.50). Test your method by invoking it four times to
  add interest to all the accounts (including Inland Revenue’s!).

1. Add a method to record the date when the account is opened and test it using
  the `Account.java`.

1. Provide an Overdraft facility in the `Account.java` file which allows a
  withdraw as long as the current balance has not reached the overdraft limit.
  Test this using the `Account.java`.
