
package com.contactvault.cvault.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contactvault.cvault.entities.User;
import com.contactvault.cvault.forms.UserForm;
import com.contactvault.cvault.helper.Message;
import com.contactvault.cvault.helper.MessageType;
import com.contactvault.cvault.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Welcome to home page");
        model.addAttribute("vaultName", "Substring Value for Vault");
        model.addAttribute("vaultDescription", "This is a description for Vault");
        model.addAttribute("vaultVersion", "1.0");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("Welcome to about page");
        return "about";
    }

    @RequestMapping("/services")
    public String services(Model model) {
        System.out.println("This is services file");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact() {
        System.out.println("Welcome to contact page");
        return "contact";
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println("Welcome to login page");
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        System.out.println("Welcome to register page");

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "register";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.println("Welcome to do register page");
        // fetch form data
        // validate user form data

        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePicture("D:\\Phoenix\\webSpring\\contact-vault\\src\\main\\resources\\static\\images\\profilePicture.jpg")
        // .build();

        if (rBindingResult.hasErrors()) {
            System.out.println("Some error found at Binding Result");
            return "register";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePicture(
                "D:\\Phoenix\\webSpring\\contact-vault\\src\\main.resources\\static\\images\\profilePicture.jpg");

        user.setEnabled(true);
        
        userService.saveUser(user);

        System.out.println(userForm);
        // save to datbase
        // success message
        Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();
        session.setAttribute("message", message);
        // add the success message

        // redirect to login page

        return "redirect:/register";

    }

}
