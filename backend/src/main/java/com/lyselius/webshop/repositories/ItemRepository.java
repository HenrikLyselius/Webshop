package com.lyselius.webshop.repositories;


import com.lyselius.webshop.dbEntities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByName(String name);
}
