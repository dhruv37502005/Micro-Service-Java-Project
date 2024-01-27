package com.micro.hotel.HotelService.services;

import com.micro.hotel.HotelService.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel saveHotel(Hotel hotel);
    //getall
    List<Hotel> getAllHotels();
    //getsingle
    Hotel getHotel(String hotelId);
    //delete
    Hotel deleteHotel(String hotelId);
    //update
    Hotel updateHotel(String hotelId, Hotel updatedHotel);
}
