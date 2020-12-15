package com.lyselius.webshop;

import com.lyselius.webshop.dbEntities.User;
import com.lyselius.webshop.dbEntities.User_role;
import com.lyselius.webshop.repositories.UserRepository;
import com.lyselius.webshop.repositories.User_roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    User_roleRepository user_roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow(() -> {
            return new UsernameNotFoundException(username + " is not in the database.");
        });

        List<User_role> list = user_roleRepository.findAllByUserID(user.get().getuserID());
        List<String> authorities = list.stream()
                                    .map(user_role -> user_role.getRole())
                                    .collect(Collectors.toList());

        return new MyUserDetails(user.get(), authorities);
    }
}
