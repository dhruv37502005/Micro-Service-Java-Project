package com.micro.user.service.external.services;

import com.micro.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="rating-service")
public interface ratingServiceUser {
        @GetMapping("/ratings/users/{userId}")
        List<Rating> getRatingByUserId(@PathVariable("userId") String userId);
}
