package com.micro.user.service.services.implement;

import com.micro.user.service.entities.Hotel;
import com.micro.user.service.entities.Rating;
import com.micro.user.service.entities.User;
import com.micro.user.service.exceptions.ResourceNotFoundException;
import com.micro.user.service.external.services.hotelServiceUser;
import com.micro.user.service.external.services.ratingServiceUser;
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

    @Autowired
    private hotelServiceUser hotelServiceUser;

    @Autowired
    private ratingServiceUser ratingServiceUser;

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
            //using Rest Template
//            Rating[] ratingsofUser = restTemplate.getForObject("http://rating-service/ratings/users/" + userinfo.getUserId(), Rating[].class);
//            logger.info("{}", ratingsofUser);
            //using Feign Client
            List<Rating> ratingsList = ratingServiceUser.getRatingByUserId(userinfo.getUserId());

            //save users rating to the list
            userinfo.setRatings(ratingsList);

            // loop to get hotel of that rating
            for(Rating rating : ratingsList) {
                //using Rest Template
//                Hotel hotel = restTemplate.getForObject("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
//                logger.info("HOTELS {}", hotel);
                //using Feign Client
                Hotel hotel = hotelServiceUser.getHotel(rating.getHotelId());
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
        //using Rest Template
        //fetch rating of the above user from Rating Service
        //http://localhost:8083/ratings/users/f4c6f89a-1d38-4e80-8276-54a8f907cf6c
//        Rating[] ratings = restTemplate.getForObject("http://rating-service/ratings/users/"+user.getUserId(), Rating[].class);
//        List<Rating> ratingsList = Arrays.stream(ratings).toList();
//        logger.info("RATINGS {}", ratings);

        //using Feign Client
        List<Rating> ratingsList = ratingServiceUser.getRatingByUserId(user.getUserId());

        for(Rating rating : ratingsList) {
            //using Rest Template
//        //fetch hotel of the above rating from Hotel Service
//        //http://localhost:8082/hotels/d2038849-c005-45d8-901c-e9fa8669436c
//            Hotel hotel = restTemplate.getForObject("http://hotel-service/hotels/" + rating.getHotelId(), Hotel.class);
//            logger.info("HOTELS {}", hotel);
            //using Feign Client
            Hotel hotel = hotelServiceUser.getHotel(rating.getHotelId());
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
