package com.micro.rating.RatingService.repositories;

import com.micro.rating.RatingService.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {

    //create custom finder methods

    //return list of ratings by user
    List<Rating> findByUserId(String userId);
    //return list of ratings of hotels
    List<Rating> findByHotelId(String hotelId);

}
