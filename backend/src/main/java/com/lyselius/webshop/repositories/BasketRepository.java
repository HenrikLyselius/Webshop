package com.lyselius.webshop.repositories;

import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {


    @Query(value = "select * from basket b where b.userID like :userID and b.active = true", nativeQuery = true)
    Optional<Basket> findByUserIDandActive(@Param("userID") long userID);


    Optional<Basket> findByBasketID(long basketID);
}
