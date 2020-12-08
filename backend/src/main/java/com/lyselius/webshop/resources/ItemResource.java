package com.lyselius.webshop.resources;


import com.lyselius.webshop.dbEntities.Item;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ItemResource {


    @Autowired
    ItemRepository itemRepository;

    @GetMapping(value = "/testitem")
    public ResponseEntity<Item> testItem()
    {
        Item newItem = new Item();
        newItem.setBaskets(null);
        newItem.setItemID(2);
        newItem.setName("Hejhej");
        newItem.setDescription("En beskri");

        return ResponseEntity.ok(newItem);
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<?> addItem(@RequestBody Map<String, String> item)
    {
        Item newItem = new Item(item.get("name"), item.get("description"), Integer.parseInt(item.get("price")));

        Optional<Item> earlierItem = itemRepository.findByName(newItem.getName());
        if(earlierItem.isPresent())
        { return ResponseEntity.status(HttpStatus.CONFLICT).body(newItem); }

        itemRepository.save(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);

    }
    

    @GetMapping(value = "/item/{searchString}")
    public ResponseEntity<?> getItemBySearchString(@PathVariable String searchString)
    {
        Optional<List<Item>> items = itemRepository.findBySearchString(searchString);

        if(items.isPresent())
        {
            return ResponseEntity.ok(items.get());
        }
        else
        {
            return ResponseEntity.ok(new ArrayList<Item>());
        }
    }
}
