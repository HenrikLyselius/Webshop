package com.lyselius.webshop.resources;

import com.lyselius.webshop.dbEntities.Basket_item;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Basket_itemRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Basket_itemResource {

    @Autowired
    Basket_itemRepository basket_itemRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/addbasketitem", method = RequestMethod.POST)
    public ResponseEntity<Basket_item> attBasketItem(@RequestBody Basket_item basket_item)
    {
        System.out.println(basket_item.getAmount());
        return ResponseEntity.ok(basket_item);
    }




    @RequestMapping(value = "/basket/additem/{basketID}/{itemID}/{change}", method = RequestMethod.PUT)
    public void addItemToBasket(@PathVariable long basketID, @PathVariable long itemID, @PathVariable int change)
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
            basket_itemRepository.save(new Basket_item(basketID, itemID, change));
        }
    }

    @GetMapping(value = "/basketitems/{basketID}")
    public ResponseEntity<List<JSONObject>> RestGetBasketItems(@PathVariable long basketID)
    {
        return ResponseEntity.ok(getBasketItems(basketID));
    }


    public List<JSONObject> getBasketItems(long basketID)
    {
        String sql = "select \n" +
                "\t(i.name) as name,\n" +
                "    (i.price) as price,\n" +
                "    i.itemID as itemID,\n" +
                "\tamount as amount\n" +
                "from basket_item \n" +
                "inner join item i\n" +
                "on basket_item.itemID = i.itemID\n" +
                "where basketID = " + basketID;

        List<JSONObject> list = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for(Map row : rows)
        {
            JSONObject obj = new JSONObject();

            obj.put("amount", row.get("amount"));
            obj.put("name", row.get("name"));
            obj.put("price", row.get("price"));
            obj.put("itemID", row.get("itemID"));

            list.add(obj);
        }

        return list;
    }
}
