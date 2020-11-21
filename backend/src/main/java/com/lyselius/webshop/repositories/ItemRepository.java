package com.lyselius.webshop.repositories;


import com.lyselius.webshop.dbEntities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Optional<Item> findByName(String name);

    @Query(value = "select * from item i where i.name like %:searchString%", nativeQuery = true)
    Optional<List<Item>> findBySearchString(@Param("searchString") String searchString);
}
