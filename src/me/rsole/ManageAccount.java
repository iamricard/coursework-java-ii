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
