package com.lyselius.webshop.dbEntities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Basket {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long basketID;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "basket_item",
                joinColumns = { @JoinColumn(name = "basketID") },
                inverseJoinColumns = { @JoinColumn(name = "itemID") })
    private List<Item> items = new ArrayList<>();


    private boolean active;

    public Basket()
    {

    }

    public Basket(User user)
    {
        this.user = user;
        this.active = true;
    }

    public Basket(Basket basket)
    {
        this.basketID = basket.getBasketID();
        this.user = basket.getUser();
        this.items = basket.getItems();
        this.active = true;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }
}
