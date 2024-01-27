package com.micro.rating.RatingService.services;

import com.micro.rating.RatingService.entities.Rating;

import java.util.List;

public interface RatingService {

    //create Ratings
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAllRatings();

    //get rating by userId
    List<Rating> getRatingByUserId(String userId);

    //get rating by hotelId
    List<Rating> getRatingByHotelId(String hotelId);

    //delete rating
    Rating deleteRating(String ratingId);

    //update rating
    Rating updateRating(String ratingId, Rating updatedRating);
}
