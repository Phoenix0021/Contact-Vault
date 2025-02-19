package com.contactvault.cvault.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.contactvault.cvault.services.EmailSender;

@Service
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private JavaMailSender eMailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {

       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(to);
         message.setSubject(subject);
            message.setText(body);
            message.setFrom("hello@demomailtrap.com");
            eMailSender.send(message);
 
    }
    
}
