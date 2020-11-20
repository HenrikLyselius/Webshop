package com.lyselius.webshop.resources;


import com.lyselius.webshop.dbEntities.Item;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
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
}
