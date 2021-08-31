package com.example.taxpro;

public class AccountLog
{
    private boolean depositOrNot;
    private double amount;
    private String dateOfTransaction;
    private String timeOfTransaction;

    public AccountLog(boolean depositOrNot, double amount,String dateOfTransaction, String timeOfTransaction)
    {
        this.depositOrNot = depositOrNot;
        this.amount = amount;
        this.dateOfTransaction = dateOfTransaction;
        this.timeOfTransaction = timeOfTransaction;
    }

    public boolean isDepositOrNot() { return depositOrNot; }
    public double getAmount() { return amount; }
    public String getDateOfTransaction() { return dateOfTransaction; }
    public String getTimeOfTransaction() { return timeOfTransaction; }
}
