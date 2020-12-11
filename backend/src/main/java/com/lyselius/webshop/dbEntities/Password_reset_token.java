package com.lyselius.webshop.dbEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Password_reset_token {



    private static final int EXPIRATION = 1000 * 60 * 60;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long tokenID;

    private String token;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userID")
    private User user;

    private Timestamp expiry_date;


    public Password_reset_token()
    {

    }

    public Password_reset_token(String token, User user)
    {
        this.token = token;
        this.user = user;
        setExpiry_date();
    }


    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Timestamp expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void setExpiry_date()
    {
        this.expiry_date = new Timestamp(System.currentTimeMillis() + EXPIRATION);
    }
}
