package com.lyselius.webshop.repositories;

import com.lyselius.webshop.dbEntities.Order_;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Order_Repository extends JpaRepository<Order_, Integer> {


    Optional<List<Order_>> findAllByExpediated(boolean expediated);

    Optional<Order_> findByOrderID(long orderID);
}
