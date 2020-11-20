package com.lyselius.webshop;

import com.lyselius.webshop.dbEntities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {


    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() {
    };

    public MyUserDetails(User user, List<String> authorities)
    {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = true;
        authorities.stream().forEach(role -> this.authorities.add(new SimpleGrantedAuthority(role)));
        //this.authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    public MyUserDetails()
    {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
