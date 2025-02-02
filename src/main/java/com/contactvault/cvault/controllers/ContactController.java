package com.contactvault.cvault.controllers;

import com.contactvault.cvault.helper.AppConstants;
import com.contactvault.cvault.helper.EmailHelper;
import com.contactvault.cvault.helper.Message;
import com.contactvault.cvault.helper.MessageType;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contactvault.cvault.entities.Contact;
import com.contactvault.cvault.entities.User;
import com.contactvault.cvault.forms.ContactForm;
import com.contactvault.cvault.forms.ContactSearchForm;
import com.contactvault.cvault.services.ContactService;
import com.contactvault.cvault.services.UserService;
import com.contactvault.cvault.services.ImageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    // add contact page: handler
    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    // save contact
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) throws IOException {
        // process the form data
        if (result.hasErrors()) {
            session.setAttribute("message",
                    Message.builder().type(MessageType.green).content("Please fill all the required fields").build());
            return "user/add_contact";
        }

        String username = EmailHelper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
      
      
        

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());

        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }

        contactService.save(contact);

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";
    }

    // view contacts
    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE +"") int size,
            @RequestParam(value = "sortField", defaultValue = "name") String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,

            Model model, Authentication authentication, HttpSession session) {

        String username = EmailHelper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortField, sortDirection);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    // search handler
    @RequestMapping("/search")
    public String searchhandler( 
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortField", defaultValue = "name") String sortField,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {

                User user = userService.getUserByEmail(EmailHelper.getEmailOfLoggedInUser(authentication));


        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), page, size, sortField, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), page, size, sortField, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), page, size, sortField, direction, user);
        }
          if (pageContact == null) {
        pageContact = new PageImpl<>(Collections.emptyList(), Pageable.unpaged(), 0);
    }
 
    model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact", pageContact);

       


        return "user/search";
    }



    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId){


        contactService.delete(contactId);   
        logger.info("Contact with id {} has been deleted", contactId);
        return "redirect:/user/contacts?deleted=true";
    }

    //update contact from view
    @RequestMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable("contactId") String contactId, Model model){
        Contact contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
         model.addAttribute("contactForm", contactForm);
         model.addAttribute("contactId", contactId);
         return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,Model model, @Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) throws IOException {
         var contact = new Contact();
        contact.setId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()){
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }

        var updatedContact = contactService.update(contact);

        model.addAttribute("message", Message.builder().content("Contact has been updated successfully").type(MessageType.green).build()); 
        return "redirect:/user/contacts/view/" + contactId;
    }
    

}
