package com.lyselius.webshop.repositories;

import com.lyselius.webshop.dbEntities.User_role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface User_roleRepository extends JpaRepository<User_role, Integer> {

    Optional<User_role> findByUserIDAndRole(long userID, String role);
    List<User_role> findAllByUserID(long userID);
}
