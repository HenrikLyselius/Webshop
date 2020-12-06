package com.lyselius.webshop.dbEntities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    long itemID;
    String name;
    String description;
    int price;


    @JsonBackReference
    @ManyToMany(mappedBy = "items")
    private List<Basket> baskets = new ArrayList<>();

    public Item()
    {

    }

    public Item(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

}
