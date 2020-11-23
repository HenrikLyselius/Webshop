package com.lyselius.webshop.resources;

import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Basket_item;
import com.lyselius.webshop.dbEntities.Item;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Basket_itemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Basket_itemResource {


    @Autowired
    Basket_itemRepository basket_itemRepository;

    @Autowired
    BasketRepository basketRepository;


    @RequestMapping(value = "/basket/additem/{basketID}/{itemID}/{change}", method = RequestMethod.PUT)
    public void addItemToBasket(@PathVariable long basketID, @PathVariable long itemID, @PathVariable int change)
    {
        System.out.println("Här i addItem");
        Optional<Basket_item> basket_item = basket_itemRepository.findByBasketIDAndItemID(basketID, itemID);
        if(basket_item.isPresent())
        {
            basket_item.get().setAmount(basket_item.get().getAmount() + change);
            basket_itemRepository.save(basket_item.get());
        }
        else
        {
            System.out.println("Här i addItem else");
            basket_itemRepository.save(new Basket_item(basketID, itemID, change));
        }
    }


}
