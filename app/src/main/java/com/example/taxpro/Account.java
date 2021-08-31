package com.example.taxpro;

public class Account
{
    private static final int DEFAULT_BALANCE=0;
    private String accountType;
    private double balance;

    public Account(String accountType)
    {
        this.accountType=accountType;
        balance=DEFAULT_BALANCE;
    }

    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }

    protected class AccountLog
    {
        private boolean depositOrNot;
        private double amount;
        private double balance;
        private String dateOfTransaction;
        private String timeOfTransaction;

        public AccountLog(boolean depositOrNot, double amount, double balance, String dateOfTransaction, String timeOfTransaction)
        {
            this.depositOrNot = depositOrNot;
            this.amount = amount;
            this.balance = balance;
            this.dateOfTransaction = dateOfTransaction;
            this.timeOfTransaction = timeOfTransaction;
        }

        public boolean isDepositOrNot() { return depositOrNot; }
        public double getAmount() { return amount; }
        public double getBalance() { return balance; }
        public String getDateOfTransaction() { return dateOfTransaction; }
        public String getTimeOfTransaction() { return timeOfTransaction; }
    }
}
