package com.example.taxpro.goods;

public abstract class Goods
{
    private String name;
    private double price;
    private int quantity; //개수

    public Goods() {}

    public Goods(String name, double price, int quantity)
    {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
