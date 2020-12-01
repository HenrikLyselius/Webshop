package com.lyselius.webshop.resources;


import com.lyselius.webshop.AuthenticationRequest;
import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.dbEntities.User_role;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.UserRepository;
import com.lyselius.webshop.repositories.User_roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserResource {



    @Autowired
    UserRepository userRepository;

    @Autowired
    User_roleRepository user_roleRepository;

    @Autowired
    BasketRepository basketRepository;


    @RequestMapping(value = "/testuser", method = RequestMethod.POST)
    public ResponseEntity<String> testUser(@RequestBody Map<String, Object> map)
    {
        System.out.println(map.get("userID"));
        return ResponseEntity.ok("Funkish!!");
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody AuthenticationRequest userData)
    {
        User newUser = new User(userData.getUsername(), userData.getPassword());
        Optional<User> userOp = userRepository.findByUsername(userData.getUsername());
        if(userOp.isPresent())
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(newUser);
        }
        else
        {
            userRepository.save(newUser);
            Basket newBasket = new Basket(newUser);
            basketRepository.save(newBasket);
            user_roleRepository.save(new User_role(newUser.getuserID(), "ROLE_CUSTOMER"));
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
    }


    @GetMapping(value = "/isadmin/{username}")
    public boolean isUserAdmin(@PathVariable String username)
    {
        Optional<User> userOp = userRepository.findByUsername(username);
        if(userOp.isEmpty()) { return false; }

        Optional<User_role> user_roleOp = user_roleRepository.findByUserIDAndRole(userOp.get().getuserID(), "ROLE_ADMIN");
        if(user_roleOp.isPresent()) { return true; };
        return false;
    }
}
