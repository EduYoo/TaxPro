package com.example.taxpro.goods;

import java.time.LocalDate;

public class InvestmentGoods extends Goods
{
    String history;

    public InvestmentGoods()
    {
        super();
    }

    public InvestmentGoods(String name, double price, int quantity)
    {
        super(name, price, quantity);
        this.history = LocalDate.now().toString();
    }

    @Override
    public String toString()
    {
        return "InvestmentGoods{" +
                "name='" + getName() +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                " history='" + history +
                '}';
    }
}
