package com.micro.user.service.services;

import com.micro.user.service.entities.User;

import java.util.List;

public interface UserService {

    //user operations
    User saveUser(User user);
    //get all users
    List<User> getAllUser();
    //get single user of given ID
    User getUser(String userId);
    //delete
    User deleteUser(String userId);
//    //update
    User updateUser(String userId, User updatedUser);

}
