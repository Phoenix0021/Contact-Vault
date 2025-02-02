package com.contactvault.cvault.services;

import java.util.List;
import java.util.Optional;

import com.contactvault.cvault.entities.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User> getAllUsers();
    User getUserByEmail(String email);

    

}
