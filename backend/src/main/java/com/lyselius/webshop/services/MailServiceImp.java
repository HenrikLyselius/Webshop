package com.lyselius.webshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImp{


    @Autowired
    JavaMailSender javaMailSender;

    public void sendNewPasswordEmail(String address, String URL) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(address);
        msg.setSubject("Välj ett nytt lösenord.");
        msg.setText("Klicka på länken för att välja ett nytt lösenord. " + URL);

        javaMailSender.send(msg);

    }

}
