package com.micro.user.service.services.implement;

import com.micro.user.service.entities.User;
import com.micro.user.service.exceptions.ResourceNotFoundException;
import com.micro.user.service.repositories.UserRepository;
import com.micro.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImplementation  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ userId));
    }

    @Override
    public User deleteUser(String userId) {
        //check if user exist
        User userToDelete = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ userId));
        userRepository.delete(userToDelete);
        return userToDelete;
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        //check if user exist
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ userId));
        //update feilds
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAbout(updatedUser.getAbout());
        return userRepository.save(existingUser);
    }
}
