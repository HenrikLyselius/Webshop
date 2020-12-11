package com.lyselius.webshop.resources;


import com.lyselius.webshop.dbEntities.Basket;
import com.lyselius.webshop.dbEntities.Password_reset_token;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.dbEntities.User_role;
import com.lyselius.webshop.repositories.BasketRepository;
import com.lyselius.webshop.repositories.Password_reset_tokenRepository;
import com.lyselius.webshop.repositories.UserRepository;
import com.lyselius.webshop.repositories.User_roleRepository;
import com.lyselius.webshop.services.MailServiceImp;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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

    @Autowired
    Password_reset_tokenRepository password_reset_tokenRepository;


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
            newUser.setPassword(hashPassword(newUser.getPassword()));
            userRepository.save(newUser);
            Basket newBasket = new Basket(newUser);
            basketRepository.save(newBasket);
            user_roleRepository.save(new User_role(newUser.getuserID(), "ROLE_CUSTOMER"));
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
    }



    @GetMapping(value ="/user/forgotpassword/{username}")
    public ResponseEntity<JSONObject> getPasswordTokenAndSendMail(@PathVariable String username, HttpServletRequest request)
    {
        JSONObject response = new JSONObject();
        Optional<User> userOp = userRepository.findByUsername(username);

        if(userOp.isPresent())
        {
           User user = userOp.get();

            // Generate a new token
            String tokenString = generateResetPasswordToken();
            Optional<Password_reset_token> tokenOp = password_reset_tokenRepository.findByUserID(user.getuserID());
            if(tokenOp.isPresent())
            {
                Password_reset_token token = tokenOp.get();
                token.setToken(tokenString);
                token.setExpiry_date();
                password_reset_tokenRepository.save(token);
            }
            else
            {
                Password_reset_token token = new Password_reset_token(tokenString, user);
                password_reset_tokenRepository.save(token);
            }

            mailService.sendNewPasswordEmail(user.getEmail(), getResetPasswordURL(request, tokenString, user.getUsername()));

            return ResponseEntity.ok(response);
        }

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



    @RequestMapping(value = "/user/updatepassword", method = RequestMethod.PUT)
    public ResponseEntity<JSONObject> updatePassword(@RequestBody Map<String, String> data)
    {
        JSONObject response = new JSONObject();
        User user = userRepository.findByUsername(data.get("username")).get();
        Optional<Password_reset_token> tokenOp = password_reset_tokenRepository.findByToken(data.get("token"));

        if(tokenOp.isPresent())
        {
            Password_reset_token token = tokenOp.get();

            if(token.getUser().getuserID() == user.getuserID() &&
                    token.getExpiry_date().compareTo(new Date(System.currentTimeMillis())) > 0)
            {
                user.setPassword(hashPassword(data.get("newPassword")));
                userRepository.save(user);
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }



    private String generateResetPasswordToken()
    {
        Random random = new Random();
        String abc = "abcdefghijklmnopqrstuvwxyz1234567890";

        StringBuilder newToken = new StringBuilder("");

        for(int i = 0; i < 60; i++)
        {
            newToken.append(abc.charAt(random.nextInt(abc.length())));
        }

        return newToken.toString();
    }


    private String hashPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    @GetMapping(value = "/rehashpassword/{username}")
    public String rehashPassword(@PathVariable String username)
    {
        Optional<User> userOp = userRepository.findByUsername(username);

        if(userOp.isPresent())
        {
            User user = userOp.get();
            user.setPassword(hashPassword(user.getPassword()));
            userRepository.save(user);

            return "OK";
        }

        return "Problem";
    }




    public static String getResetPasswordURL(HttpServletRequest request, String token, String username)
    {
        return "http://localhost:4200/resetpassword/" + token + "/" + username;
        //return "http://80.216.204.53:4200/resetpassword/" + token + "/" + username;
    }
}
