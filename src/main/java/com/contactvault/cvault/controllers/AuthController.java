package com.contactvault.cvault.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactvault.cvault.entities.User;
import com.contactvault.cvault.repositories.UserRepo;

@Controller
@RequestMapping("/auth")
public class AuthController {

///auth/verify-email?token=
    @Autowired
    private UserRepo userRepo;


    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {

        User user = userRepo.findByEmailVerificationToken(token);
        if (user != null) {
            if(user.getEmailVerificationToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
            }
            
            return "user/success_page";
        }
        else {
            return "user/error_page";
        }
     }
    
}
