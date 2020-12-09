package com.lyselius.webshop.resources;


import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Basket_item;
import com.lyselius.webshop.dbEntities.Order_;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Basket_itemRepository;
import com.lyselius.webshop.repositories.Order_Repository;
import org.aspectj.weaver.ast.Or;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    Basket_itemResource basket_itemResource;



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





    /*@GetMapping(value = "/orders/notexpediated")
    public ResponseEntity<List<Order_>> getNotExpediatedOrders()
    {
        Optional<List<Order_>> ordersOp = order_repository.findAllByExpediated(false);

        if(ordersOp.isPresent())
        {
            return ResponseEntity.ok(ordersOp.get());
        }

        return ResponseEntity.ok(new ArrayList<>());
    }*/

    @GetMapping(value = "/orders/notexpediated")
    public ResponseEntity<JSONObject> getNotExpediatedOrders()
    {
        JSONObject response = new JSONObject();
        List<JSONObject> list = new ArrayList<>();

        Optional<List<Order_>> ordersOp = order_repository.findAllByExpediated(false);

        if(ordersOp.isPresent())
        {

            for(int i = 0; i < ordersOp.get().size(); i++)
            {
                JSONObject obj = new JSONObject();

                obj.put("orderID", ordersOp.get().get(i).getOrderID());
                obj.put("basketID", ordersOp.get().get(i).getBasketID());
                obj.put("date", ordersOp.get().get(i).getDate_());
                obj.put("expediated", ordersOp.get().get(i).isExpediated());
                obj.put("orderDetails", getOrderDetails(ordersOp.get().get(i).getOrderID()));

                list.add(obj);
            }

            response.put("orderList", list);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @RequestMapping(value = "/order/expediate/{orderID}", method = RequestMethod.PUT)
    public void expediateOrder(@PathVariable long orderID)
    {
        Optional<Order_> orderOp = order_repository.findByOrderID(orderID);
        orderOp.get().setExpediated(true);
        order_repository.save(orderOp.get());

    }


    @RequestMapping(value = "/order/getdetails/{orderID}")
    public ResponseEntity<JSONObject> getOrderDetailsREST(@PathVariable long orderID)
    {
        JSONObject response = getOrderDetails(orderID);
        if(response == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); }

        return ResponseEntity.ok(response);

    }

    public JSONObject getOrderDetails(long orderID)
    {
        Optional<Order_> orderOp = order_repository.findByOrderID(orderID);
        JSONObject response = new JSONObject();

        if(orderOp.isPresent())
        {
            long basketID = orderOp.get().getBasketID();

            response.put("userID", basketRepository.findByBasketID(basketID).get().getUser().getuserID());
            response.put("username", basketRepository.findByBasketID(basketID).get().getUser().getUsername());
            response.put("items", basket_itemResource.getBasketItems(basketID));

            return response;
        }

        return null;
    }
}
