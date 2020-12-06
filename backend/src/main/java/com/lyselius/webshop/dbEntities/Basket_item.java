package com.lyselius.webshop.dbEntities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Basket_item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long basket_itemID;
    private long basketID;
    private long itemID;
    private int amount;


    public Basket_item()
    {

    }

    public Basket_item(long basketID, long itemID, int amount)
    {
        this.basketID = basketID;
        this.itemID = itemID;
        this.amount = amount;
    }



    public long getBasket_itemID() {
        return basket_itemID;
    }

    public void setBasket_itemID(long basket_itemID) {
        this.basket_itemID = basket_itemID;
    }

    public long getBasketID() {
        return basketID;
    }

    public void setBasketID(long basketID) {
        this.basketID = basketID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
