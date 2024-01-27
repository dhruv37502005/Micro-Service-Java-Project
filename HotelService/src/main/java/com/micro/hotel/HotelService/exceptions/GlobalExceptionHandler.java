package com.micro.hotel.HotelService.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundHandler(ResourceNotFoundException ex){
        Map map = new HashMap();
        map.put("message", ex.getMessage());
        map.put("status", HttpStatus.NOT_FOUND);
        map.put("success", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
}
