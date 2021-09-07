package com.example.taxpro.goods;

public abstract class Goods
{
    private String name;
    private double price;
    private int inventory; //개수

    public Goods() {}

    public Goods(String name, double price, int inventory)
    {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getInventory() { return inventory; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setInventory(int inventory) { this.inventory = inventory; }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name +
                ", price=" + price +
                ", inventory=" + inventory +
                '}';
    }
}
