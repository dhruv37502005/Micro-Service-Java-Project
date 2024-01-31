package com.micro.user.service.services.implement;

import com.micro.user.service.entities.Hotel;
import com.micro.user.service.entities.Rating;
import com.micro.user.service.entities.User;
import com.micro.user.service.exceptions.ResourceNotFoundException;
import com.micro.user.service.repositories.UserRepository;
import com.micro.user.service.services.UserService;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImplementation  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;



    private Logger logger =LoggerFactory.getLogger(UserServiceImplementation.class);
    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {

        List<User> usersList = userRepository.findAll();
//        loop for users and get their ratings data
        for (User userinfo : usersList) {
            Rating[] ratingsofUser = restTemplate.getForObject("http://localhost:8083/ratings/users/" + userinfo.getUserId(), Rating[].class);
            logger.info("{}", ratingsofUser);
            List<Rating> ratingsList = Arrays.stream(ratingsofUser).toList();
            userinfo.setRatings(ratingsList);
            // loop to get hotel of that rating
            for(Rating rating : ratingsList) {
                Hotel hotel = restTemplate.getForObject("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
                logger.info("HOTELS {}", hotel);
                rating.setHotel(hotel);
            }
            userinfo.setRatings(ratingsList);
        }
        return usersList;
    }

    @Override
    public User getUser(String userId) {
        //return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ userId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ userId));
        //fetch rating of the above user from Rating Service
        //http://localhost:8083/ratings/users/f4c6f89a-1d38-4e80-8276-54a8f907cf6c
        Rating[] ratings = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("RATINGS {}", ratings);
        List<Rating> ratingsList = Arrays.stream(ratings).toList();
//        //fetch hotel of the above rating from Hotel Service
//        //http://localhost:8082/hotels/d2038849-c005-45d8-901c-e9fa8669436c
        for(Rating rating : ratingsList) {
            Hotel hotel = restTemplate.getForObject("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
            logger.info("HOTELS {}", hotel);
            rating.setHotel(hotel);
        }
        user.setRatings(ratingsList);
        return user;

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
