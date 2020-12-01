package com.lyselius.webshop.dbEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Order_ {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long orderID;
    private long basketID;
    private Date date_;
    private boolean expediated;


    public Order_()
    {

    }


    public Order_(long basketID)
    {
        this.basketID = basketID;
        this.date_ = new Date(System.currentTimeMillis());
        this.expediated = false;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getBasketID() {
        return basketID;
    }

    public void setBasketID(long basketID) {
        this.basketID = basketID;
    }

    public Date getDate_() {
        return date_;
    }

    public void setDate_(Date date_) {
        this.date_ = date_;
    }

    public boolean isExpediated() {
        return expediated;
    }

    public void setExpediated(boolean expediated) {
        this.expediated = expediated;
    }
}
