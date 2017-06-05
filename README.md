# Coursework Java - Part II

## Test output

```
450.0
100.0
525.0
20230715	Barack Obama	$525.00
31558040	Bill Gates	$100.00
44003050	Tom Cruise	$600.00
Total in bank is: 1225.0
Added 1.5% interest to account #20230715
New balance is: 669.375
Added 1.5% interest to account #31558040
New balance is: 127.49999999999999
Added 1.5% interest to account #44003050
New balance is: 765.0
Added 1.5% interest to account #1
New balance is: 275.625

Account # 20230715 was created on Mon Jun 05 21:14:28 BST 2017
Account # 31558040 was created on Mon Jun 05 21:14:28 BST 2017
Account # 44003050 was created on Mon Jun 05 21:14:28 BST 2017
Account # 1 was created on Mon Jun 05 21:14:28 BST 2017
```

## Source code listing

```java
// Account.java

package me.rsole;

import java.text.NumberFormat;
import java.util.Date;

public class Account {
  private int accountNumber;
  private double balance;
  private String name;
  private Date openingDate;
  private double maxOverdraft;

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

  double withdraw(double amount) {
    return withdraw(amount, 0);
  }

  double withdraw(double amount, double fee) {
    double withdrawal = amount + fee;
    double newBalance = balance - withdrawal;

    if (newBalance < (0 - maxOverdraft)) {
      throw new InsufficientFundsError();
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

  class InsufficientFundsError extends Error {
    InsufficientFundsError() {
    }

    InsufficientFundsError(String message) {
      super(message);
    }
  }
}
```

```java
// ManageAccount.java

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
      accounts.get(1).withdraw(400);
      System.out.println(accounts.get(1).getBalance());
    } catch (Account.InsufficientFundsError e) {
      System.out.println("Not enough funds on account " + accounts.get(1).getAccountNumber());
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
      System.out.println("Added 1.5% interest to account #" + a.getAccountNumber());
      System.out.println("New balance is: " + a.getBalance());
    }

    inlandRevenue.addInterest(.015);
    System.out.println("Added 1.5% interest to account #" + inlandRevenue.getAccountNumber());
    System.out.println("New balance is: " + inlandRevenue.getBalance() + "\n");

    for (Account a : accounts) {
      System.out.println("Account # " + a.getAccountNumber() + " was created on " + a.getOpeningDate());
    }

    System.out.println(
      "Account # " +
        inlandRevenue.getAccountNumber() +
        " was created on " +
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
      } catch (Account.InsufficientFundsError e) {
        System.out.println("Account # " + a.getAccountNumber() + " had insufficient funds to pay their taxes.");
      }
    }

    return t;
  }
}
```