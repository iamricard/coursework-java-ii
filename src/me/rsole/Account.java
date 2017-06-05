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