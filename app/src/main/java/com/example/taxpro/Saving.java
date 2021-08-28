package com.example.taxpro;

import java.io.Serializable;
import java.util.Date;

public class Saving implements Serializable
{
    private String type;
    private double rate;
    private int amount;

    private Date registrationDate;
    private int totalTerm;
    private int period; // 15,30 etc...

    private String name;
    private int number;

    public Saving() {}

    @Override
    public String toString()
    {
        return "Saving{" +
                "type='" + type + '\'' +
                ", rate=" + rate +
                ", amount=" + amount +
                ", period=" + period +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    public String getType() { return type; }
    public double getRate() { return rate; }
    public int getAmount() { return amount; }
    public Date getRegistrationDate() { return registrationDate; }
    public int getTotalTerm() { return totalTerm; }
    public int getPeriod() { return period; }
    public String getName() { return name; }
    public int getNumber() { return number; }

    public void setType(String type) { this.type = type; }
    public void setRate(double rate) { this.rate = rate; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    public void setTotalTerm(int totalTerm) { this.totalTerm = totalTerm; }
    public void setPeriod(int period) { this.period = period; }
    public void setName(String name) { this.name = name; }
    public void setNumber(int number) { this.number = number; }
}
