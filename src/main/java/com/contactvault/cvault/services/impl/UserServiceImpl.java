package com.contactvault.cvault.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contactvault.cvault.entities.User;
import com.contactvault.cvault.helper.AppConstants;
import com.contactvault.cvault.helper.EmailHelper;
import com.contactvault.cvault.helper.ResourceNotFoundException;
import com.contactvault.cvault.repositories.UserRepo;
import com.contactvault.cvault.services.EmailSender;
import com.contactvault.cvault.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private EmailSender emailSender;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // user id has to be generated
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        System.out.println("User SAVED SUCCESFULLY");
        

        
        String emailToken = UUID.randomUUID().toString();
        String emailLink = EmailHelper.getLinkForEmailVerificatiton(emailToken);
        //send email to the user
        user.setEmailVerificationToken(emailToken);
        User savedUser =  userRepo.save(user);
        emailSender.sendEmail(savedUser.getEmail(), "Email Verification", emailLink);
        
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Reousrce not available"));

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setProfilePicture(user.getProfilePicture());
        user2.setEnabled(user.isEnabled());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        return Optional.of(userRepo.save(user2));
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reousrce not available"));

    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.findByEmail(email) != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    }

}
