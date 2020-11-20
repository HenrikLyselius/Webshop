package com.lyselius.webshop;

import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class DBConnectionTest {


    @Autowired
    UserRepository customerRep;

    public User saveCustomer(User user)
    {
        customerRep.save(user);
        return user;
    }
}
