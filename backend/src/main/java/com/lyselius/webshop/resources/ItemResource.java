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

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<Item> addItem(@RequestBody Item item)
    {
        Optional<Item> earlierItem = itemRepository.findByName(item.getName());
        if(earlierItem.isPresent())
        { return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(item); }

        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
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
