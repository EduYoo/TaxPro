package com.example.taxpro.goods;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoodsLog
{
    private boolean isSelling;
    private Goods goods;
    private String buyer;
    private String buyer_name;
    private String seller;
    private String seller_name;

    private String dateOfTransaction;
    private String timeOfTransaction;

    public GoodsLog(Goods goods, String buyer, String buyer_name, String seller, String seller_name)
    {
        this.isSelling=true;
        this.goods = goods;
        this.buyer = buyer;
        this.buyer_name = buyer_name;
        this.seller = seller;
        this.seller_name = seller_name;
        this.dateOfTransaction= LocalDate.now().toString();
        this.timeOfTransaction=LocalTime.now().toString();
    }

    public GoodsLog(boolean isSelling, InvestmentGoods goods, String buyer, String buyer_name, String seller, String seller_name)
    {
        this.isSelling=isSelling;
        this.goods = goods;
        this.buyer = buyer;
        this.buyer_name = buyer_name;
        this.seller = seller;
        this.seller_name = seller_name;
        this.dateOfTransaction= LocalDate.now().toString();
        this.timeOfTransaction=LocalTime.now().toString();
    }

    public String getDateOfTransaction() { return dateOfTransaction; }
    public String getTimeOfTransaction() { return timeOfTransaction; }
}
