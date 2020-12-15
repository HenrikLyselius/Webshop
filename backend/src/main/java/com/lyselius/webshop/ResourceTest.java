package com.lyselius.webshop;


import com.lyselius.webshop.dbEntities.Role;
import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.dbEntities.User_role;
import com.lyselius.webshop.repositories.RoleRepository;
import com.lyselius.webshop.repositories.UserRepository;
import com.lyselius.webshop.repositories.User_roleRepository;
import com.lyselius.webshop.services.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@CrossOrigin
public class ResourceTest {


    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    User_roleRepository user_roleRepository;


    @GetMapping("/hello")
    public String hello()
    {
        return "Hello från din nya webshop!.";
    }



    @GetMapping("/usertest")
    public String usertest()
    {
        return "Usertest här!!!!!!!";
    }

    @GetMapping("/admintest")
    public ResponseEntity<?> admintest()
    {
        return ResponseEntity.ok(new AuthenticationResponse("Admintest här!!!!!!!!!!"));
    }


    @GetMapping("/mappingtest")
    public void mappingTest()
    {
        Optional<User> user = userRepository.findByUsername("admin2");
        if(user.isPresent())
        {
            System.out.println(user.get().getRoles().get(0).getDescription());
        }
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest ar) throws Exception
    {
        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(ar.getUsername(), ar.getPassword())
            );
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(ar.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


}
