package com.lyselius.webshop.resources;


import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Order_;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Order_Repository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Order_Resource {



    @Autowired
    Order_Repository order_repository;

    @Autowired
    BasketRepository basketRepository;



    @RequestMapping(value = "/order/{basketID}", method = RequestMethod.POST)
    public ResponseEntity<Order_> createOrder(@PathVariable long basketID)
    {
        // Make order
        Order_ order_ = new Order_(basketID);
        order_repository.save(order_);

        // Set basket inactive and create a new basket

        Optional<Basket> basketOp = basketRepository.findByBasketID(basketID);
        basketOp.get().setActive(false);
        basketRepository.save(basketOp.get());

        User user = basketOp.get().getUser();
        Basket newBasket = new Basket(user);
        basketRepository.save(newBasket);

        return ResponseEntity.ok(order_);
    }





    @GetMapping(value = "/orders/notexpediated")
    public ResponseEntity<List<Order_>> getNotExpediatedOrders()
    {
        Optional<List<Order_>> ordersOp = order_repository.findAllByExpediated(false);

        if(ordersOp.isPresent())
        {
            return ResponseEntity.ok(ordersOp.get());
        }

        return ResponseEntity.ok(new ArrayList<>());
    }

    @RequestMapping(value = "/order/expediate/{orderID}", method = RequestMethod.PUT)
    public void expediateOrder(@PathVariable long orderID)
    {
        Optional<Order_> orderOp = order_repository.findByOrderID(orderID);
        orderOp.get().setExpediated(true);
        order_repository.save(orderOp.get());

    }

}
