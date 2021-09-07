package com.example.taxpro.goods;

import java.time.LocalDate;

public class InvestmentGoods extends Goods
{
    private String history;
    private int buyingQuantity;

    public InvestmentGoods()
    {
        super();
    }

    public InvestmentGoods(String name, double price, int quantity)
    {
        super(name, price, quantity);
        this.history = LocalDate.now().toString();
    }

    public void setBuyingQuantity(int buyingQuantity) { this.buyingQuantity = buyingQuantity; }

    public int getBuyingQuantity() { return buyingQuantity; }

    @Override
    public String toString()
    {
        return "InvestmentGoods{" +
                "name='" + getName() +
                ", price=" + getPrice() +
                ", quantity=" + getInventory() +
                " history='" + history +
                '}';
    }
}
