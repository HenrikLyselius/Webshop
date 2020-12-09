package com.lyselius.webshop.resources;


import com.lyselius.webshop.AuthenticationRequest;
import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.dbEntities.User_role;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.UserRepository;
import com.lyselius.webshop.repositories.User_roleRepository;
import com.lyselius.webshop.services.MailServiceImp;
import org.json.simple.JSONObject;
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

    @Autowired
    MailServiceImp mailService;




    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> userData)
    {
        User newUser = new User(userData.get("username"), userData.get("password"), userData.get("email"));
        Optional<User> userOp = userRepository.findByUsername(userData.get("username"));
        if(userOp.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(newUser);
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



    @GetMapping(value ="/user/forgotpassword/{username}")
    public ResponseEntity<JSONObject> setNewPasswordAndSendMail(@PathVariable String username)
    {
        JSONObject response = new JSONObject();
        Optional<User> userOp = userRepository.findByUsername(username);

        if(userOp.isPresent())
        {
           User user = userOp.get();


            // Generate a new password.
            String newPassword = "Nytt är det här ser du.";


            mailService.sendNewPasswordEmail(user.getEmail(), newPassword);

            response.put("message", "Jappis");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Nejnej");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
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
