package com.micro.rating.RatingService.controllers;

import com.micro.rating.RatingService.entities.Rating;
import com.micro.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RequestController {

    @Autowired
    private RatingService ratingService;
    //create rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        Rating rating1 = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

//    @Autowired
//    private RestTemplate restTemplate;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsBuUserId(@PathVariable String userId){
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
        //updating to use restTemplate

    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId){
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }

    @DeleteMapping("/{ratingId}")
    public  ResponseEntity<Rating> delete(@PathVariable String ratingId){
        return ResponseEntity.ok(ratingService.deleteRating(ratingId));
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Rating> update(@PathVariable String ratingId,@RequestBody Rating updatedRating){
        return ResponseEntity.ok(ratingService.updateRating(ratingId, updatedRating));
    }

}
