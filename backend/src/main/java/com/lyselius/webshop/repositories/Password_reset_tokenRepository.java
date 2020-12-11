package com.lyselius.webshop.repositories;

import com.lyselius.webshop.dbEntities.Password_reset_token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Password_reset_tokenRepository extends JpaRepository<Password_reset_token, Integer> {



    @Query(value = "select * from password_reset_token p where p.userID like :userID", nativeQuery = true)
    Optional<Password_reset_token> findByUserID(@Param("userID") long userID);

    /*Optional<Password_reset_token> findByUserID(long userID);*/

    Optional<Password_reset_token> findByTokenID(long tokenID);

    Optional<Password_reset_token> findByToken(String token);
}
