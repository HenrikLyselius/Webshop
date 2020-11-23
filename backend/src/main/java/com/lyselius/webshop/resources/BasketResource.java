package com.lyselius.webshop.resources;


import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Basket_item;
import com.lyselius.webshop.dbEntities.Item;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Basket_itemRepository;
import com.lyselius.webshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BasketResource {


    @Autowired
    BasketRepository basketRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/basket/{username}")
    public ResponseEntity<Basket> getActiveBasket(@PathVariable String username)
    {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.get();
        long userID = user.getuserID();
        Optional<Basket> basketOptional = basketRepository.findByUserIDandActive(userID);
        Basket basket = basketOptional.get();

        return ResponseEntity.ok(basket);
    }

    @RequestMapping(value = "/basket", method = RequestMethod.POST)
    public ResponseEntity<Basket> createBasket(@RequestBody User user)
    {
        Basket basket = new Basket(user);
        basketRepository.save(basket);

        return ResponseEntity.ok(basket);
    }

    @RequestMapping(value = "/basket", method = RequestMethod.PUT)
    public void updateBasket(@RequestBody Basket basket)
    {
        basketRepository.save(basket);
    }

    @GetMapping(value ="/basketitems/{basketID}")
    public ResponseEntity<List<Item>> getBasketItems(@PathVariable long basketID)
    {
        Optional<Basket> basket = basketRepository.findByBasketID(basketID);

        if(basket.isPresent())
        {
            return ResponseEntity.ok(basket.get().getItems());
        }
        return ResponseEntity.ok(new ArrayList<Item>());
    }
}
