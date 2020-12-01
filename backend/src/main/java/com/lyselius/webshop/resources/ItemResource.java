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
    public ResponseEntity<?> addItem(@RequestBody Item item)
    {
        System.out.println(item.getName());
        /*Item newItem = new Item(item.getName(), item.getDescription());

        Optional<Item> earlierItem = itemRepository.findByName(item.getName());
        if(earlierItem.isPresent())
        { return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(item); }


        itemRepository.save(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);*/
        return ResponseEntity.ok(item);
    }

   /* @RequestMapping(value = "/item/{name}/{description}", method = RequestMethod.POST)
    public ResponseEntity<?> addItem(@PathVariable String name, @PathVariable String description)
    {
        Item newItem = new Item(name, description);

        Optional<Item> earlierItem = itemRepository.findByName(name);
        if(earlierItem.isPresent())
        { return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(newItem); }


        itemRepository.save(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }*/

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
