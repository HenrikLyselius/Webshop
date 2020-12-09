package com.lyselius.webshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImp{


    @Autowired
    JavaMailSender javaMailSender;

    public void sendNewPasswordEmail(String address, String newPassword) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(address);
        msg.setSubject("New Password");
        msg.setText("Här är ditt nya lösenord till Amazing Webshop: " + newPassword);

        javaMailSender.send(msg);

    }

}
