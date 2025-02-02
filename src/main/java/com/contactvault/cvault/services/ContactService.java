package com.contactvault.cvault.services;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.contactvault.cvault.entities.Contact;
import com.contactvault.cvault.entities.User;
import com.contactvault.cvault.forms.ContactForm;

import jakarta.validation.Valid;

@Service
public interface ContactService {
    

    Contact save(Contact contact);
    Contact update(Contact contact);
    void delete(String id);
    //search by name or email or phone number
    // List<Contact> searchByName(String name, String email, String phoneNumber);

    List<Contact> getAll();
    Contact getById(String id);
    List<Contact> getByUserId(String userId);
    Page<Contact> getByUser(User user, int page, int size , String sortField, String sortDirection);


    //seach Method
    Page<Contact> searchByName(String nameKeyword, int page, int size, String sortBy, String order, User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int page, int size, String sortBy, String order, User user);
    Page<Contact> searchByEmail(String emailKeyword, int page, int size, String sortBy, String order, User user);
     
 
}
