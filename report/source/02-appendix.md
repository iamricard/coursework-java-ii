# Appendix

## Account.java

```java
package me.rsole;

import java.text.NumberFormat;
import java.util.Date;

public class Account {
  private int accountNumber;
  private double balance;
  private String name;
  private Date openingDate;
  private double maxOverdraft;
  private String INSUFFICIENT_FUNDS_MSG = "Insufficient funds on account # %d";

  Account(String name, int accountNumber) {
    this(name, accountNumber, 0);
  }

  Account(String name, int accountNumber, double initialBalance) {
    this.name = name;
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    this.openingDate = new Date();
    this.maxOverdraft = 0;
  }

  void deposit(double amount) {
    balance += amount;
  }

  double withdraw(double amount) throws InsufficientFundsException {
    return withdraw(amount, 0);
  }

  double withdraw(double amount, double fee) throws InsufficientFundsException {
    double withdrawal = amount + fee;
    double newBalance = balance - withdrawal;

    if (newBalance < (0 - maxOverdraft)) {
      throw new InsufficientFundsException(String.format(
        INSUFFICIENT_FUNDS_MSG, accountNumber
      ));
    }

    balance = newBalance;
    return withdrawal;
  }

  double getBalance() {
    return balance;
  }

  int getAccountNumber() {
    return accountNumber;
  }

  void addInterest(double i) {
    balance = balance * i * 100;
  }

  Date getOpeningDate() {
    return openingDate;
  }

  public double getMaxOverdraft() {
    return maxOverdraft;
  }

  public void setMaxOverdraft(double maxOverdraft) {
    this.maxOverdraft = maxOverdraft;
  }

  public String toString() {
    NumberFormat fmt = NumberFormat.getCurrencyInstance();
    return (accountNumber + "\t" + name + "\t" + fmt.format(balance));
  }

  class InsufficientFundsException extends Exception {
    InsufficientFundsException() {
    }

    InsufficientFundsException(String message) {
      super(message);
    }
  }
}
```

## ManageAccount.java

```java
package me.rsole;

import java.util.ArrayList;
import java.util.List;

public class ManageAccount {
  public static void main(String[] args) {
    List<Account> accounts = new ArrayList<>();
    accounts.add(new Account("Barack Obama", 20230715, 400));
    accounts.add(new Account("Bill Gates", 31558040, 500));
    accounts.add(new Account("Tom Cruise", 44003050, 600));

    accounts.get(0).deposit(50);
    System.out.println(accounts.get(0).getBalance());

    try {
      accounts.get(1).withdraw(10000);
      System.out.println(accounts.get(1).getBalance());
    } catch (Account.InsufficientFundsException e) {
      System.out.println(e.getMessage());
    }

    accounts.get(0).deposit(75);
    System.out.println(accounts.get(0).getBalance());

    for (Account a : accounts) {
      System.out.println(a);
    }

    System.out.println("Total in bank is: " + getTotal(accounts));

    Account inlandRevenue = new Account("Inland Revenue", 1);
    inlandRevenue.deposit(tax(accounts));

    for (Account a : accounts) {
      a.addInterest(.015);
      System.out.println(
        "Added 1.5% interest to account #" + a.getAccountNumber()
      );
      System.out.println("New balance is: " + a.getBalance());
    }

    inlandRevenue.addInterest(.015);
    System.out.println(
      "Added 1.5% interest to account #" + inlandRevenue.getAccountNumber()
    );
    System.out.println("New balance is: " + inlandRevenue.getBalance());

    for (Account a : accounts) {
      System.out.format(
        "Account # %d was created on %s.\n",
        a.getAccountNumber(),
        a.getOpeningDate()
      );
    }

    System.out.format(
      "Account # %d was created on %s.\n",
      inlandRevenue.getAccountNumber(),
      inlandRevenue.getOpeningDate()
    );
  }

  private static double getTotal(List<Account> accounts) {
    return accounts
      .stream()
      .map(Account::getBalance)
      .reduce(0.0, (total, balance) -> total + balance);
  }

  private static double tax(List<Account> accounts) {
    double t = 0;

    for (Account a : accounts) {
      try {
        t += a.withdraw(a.getBalance() * .15);
      } catch (Account.InsufficientFundsException e) {
        System.out.format(
          "Account # %d had insufficient funds to pay their taxes.\n",
          a.getAccountNumber()
        );
      }
    }

    return t;
  }
}
```
