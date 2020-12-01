package com.lyselius.webshop.resources;

import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Basket_item;
import com.lyselius.webshop.dbEntities.Item;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Basket_itemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Basket_itemResource {


    @Autowired
    Basket_itemRepository basket_itemRepository;

    @Autowired
    BasketRepository basketRepository;

    @RequestMapping(value = "/addbasketitem", method = RequestMethod.POST)
    public ResponseEntity<Basket_item> attBasketItem(@RequestBody Basket_item basket_item)
    {
        System.out.println(basket_item.getAmount());
        return ResponseEntity.ok(basket_item);
    }




    @RequestMapping(value = "/basket/additem/{basketID}/{itemID}/{change}/{name}", method = RequestMethod.PUT)
    public void addItemToBasket(@PathVariable long basketID, @PathVariable long itemID, @PathVariable int change, @PathVariable String name)
    {
        System.out.println("Här i addItem");
        Optional<Basket_item> basket_item = basket_itemRepository.findByBasketIDAndItemID(basketID, itemID);
        if(basket_item.isPresent())
        {
            basket_item.get().setAmount(basket_item.get().getAmount() + change);
            if(basket_item.get().getAmount() < 1)
            {
                basket_itemRepository.delete(basket_item.get());
                System.out.println("Basket_item borttaget!!");

            }
            else { basket_itemRepository.save(basket_item.get()); }

        }
        else
        {
            System.out.println("Här i addItem else");
            basket_itemRepository.save(new Basket_item(basketID, itemID, change, name));
        }
    }

    @GetMapping(value = "/basketitems/{basketID}")
    public ResponseEntity<List<Basket_item>> getBasketItems(@PathVariable long basketID)
    {
        Optional<List<Basket_item>> basket_items = basket_itemRepository.findAllByBasketID(basketID);
        if(basket_items.isPresent())
        {
            System.out.println("Här i basketitems!!");
            return ResponseEntity.ok(basket_items.get());
        }
        return ResponseEntity.ok(new ArrayList<Basket_item>());
    }
}
