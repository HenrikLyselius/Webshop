package com.lyselius.webshop.repositories;

import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Basket_item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Basket_itemRepository extends JpaRepository<Basket_item, Integer> {

    Optional<Basket_item> findByBasketID(long basketID);

    @Query(value = "select * from basket_item b where b.basketID like :basketID and b.itemID like :itemID", nativeQuery = true)
    Optional<Basket_item> findByBasketIDAndItemID(@Param("basketID") long basketID, @Param("itemID") long itemID);

    Optional<List<Basket_item>> findAllByBasketID(long basketID);
}
