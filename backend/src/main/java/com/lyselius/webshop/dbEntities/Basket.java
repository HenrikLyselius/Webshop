package com.lyselius.webshop.dbEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long basketID ;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
    private boolean active;

    public Basket()
    {

    }

    public Basket(User user, boolean active)
    {
        this.user = user;
        this.active = active;
    }


    public long getBasketID() {
        return basketID;
    }

    public void setBasketID(long basketID) {
        this.basketID = basketID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
