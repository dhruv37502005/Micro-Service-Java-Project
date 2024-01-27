package com.micro.rating.RatingService.services.implement;

import com.micro.rating.RatingService.entities.Rating;
import com.micro.rating.RatingService.exceptions.ResourceNotFoundException;
import com.micro.rating.RatingService.repositories.RatingRepository;
import com.micro.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImplementation implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        //generate unique userId
        String randomRatingId = UUID.randomUUID().toString();
        rating.setRatingId(randomRatingId);
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }

    @Override
    public Rating deleteRating(String ratingId) {
        Rating ratingToDelete = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ ratingId));
        ratingRepository.delete(ratingToDelete);
        return ratingToDelete;
    }

    @Override
    public Rating updateRating(String ratingId, Rating updatedRating) {
        Rating ratingToUpdate = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server:- "+ ratingId));
        ratingToUpdate.setRating(updatedRating.getRating());
        ratingToUpdate.setFeedback(updatedRating.getFeedback());
        ratingRepository.save(ratingToUpdate);
        return ratingToUpdate;
    }

}
