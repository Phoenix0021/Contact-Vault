package com.contactvault.cvault.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contactvault.cvault.entities.Contact;
import com.contactvault.cvault.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    //fnd the contact by user

    Page<Contact> findByUser(User user, PageRequest pageable);
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContaining(  User user,String nameKeyword,Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(  User user, String phoneNumberKeyword,Pageable pageable);

    Page<Contact> findByUserAndEmailContaining( User user,String emailKeyword,  Pageable pageable);

}
