package com.example.taxpro.goods;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoodsLog
{
    private boolean isSelling;
    private Goods goods;
    private String buyer_number;
    private String buyer_name;
    private String seller_number;
    private String seller_name;

    private String dateOfTransaction;
    private String timeOfTransaction;

    public GoodsLog()
    {
        this.dateOfTransaction= LocalDate.now().toString();
        this.timeOfTransaction=LocalTime.now().toString();
    }

    public GoodsLog(StoreGoods goods)
    {
        this.goods = goods;
    }

    public GoodsLog(InvestmentGoods goods)
    {
        this.goods = goods;
    }

    public void setSelling(boolean selling) { isSelling = selling; }
    public void setGoods(Goods goods) { this.goods = goods; }
    public void setBuyer_number(String buyer_number) { this.buyer_number = buyer_number; }
    public void setBuyer_name(String buyer_name) { this.buyer_name = buyer_name; }
    public void setSeller_number(String seller_number) { this.seller_number = seller_number; }
    public void setSeller_name(String seller_name) { this.seller_name = seller_name; }

    public Goods getGoods() { return goods; }
    public String getBuyer_number() { return buyer_number; }
    public String getBuyer_name() { return buyer_name; }
    public String getDateOfTransaction() { return dateOfTransaction; }
    public String getTimeOfTransaction() { return timeOfTransaction; }
}
