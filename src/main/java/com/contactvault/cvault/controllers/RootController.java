package com.contactvault.cvault.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.contactvault.cvault.entities.User;
import com.contactvault.cvault.helper.EmailHelper;
import com.contactvault.cvault.services.UserService;

@ControllerAdvice
public class RootController {

    // belw methods will get executed for all the request
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        String username = EmailHelper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);
        User user = userService.getUserByEmail(username);
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedInUser", user);

    }



}
