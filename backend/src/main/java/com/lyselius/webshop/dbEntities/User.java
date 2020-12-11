package com.lyselius.webshop.dbEntities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private long userID;
    private String username;
    private String password;
    private String email;


    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user",
            optional = true)
    private Password_reset_token password_reset_token;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Basket> baskets = new ArrayList<Basket>();

    @ManyToMany
    @JoinTable(name = "user_role",
                joinColumns = { @JoinColumn(name = "userID") },
                inverseJoinColumns = { @JoinColumn(name = "role") })
    private List<Role> roles = new ArrayList<Role>();


    public User()
    {

    }

    public User(String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getuserID() {
        return userID;
    }

    public void setuserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void addRole(Role role)
    {
        roles.add(role);
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }

    public Password_reset_token getPasswordResetToken() {
        return password_reset_token;
    }

    public void setPasswordResetToken(Password_reset_token passwordResetToken) {
        this.password_reset_token = passwordResetToken;
    }
}
