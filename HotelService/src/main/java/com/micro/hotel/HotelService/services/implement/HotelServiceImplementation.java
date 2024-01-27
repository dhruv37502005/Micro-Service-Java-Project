package com.micro.hotel.HotelService.services.implement;

import com.micro.hotel.HotelService.entities.Hotel;
import com.micro.hotel.HotelService.repositories.HotelRepository;
import com.micro.hotel.HotelService.services.HotelService;
import com.micro.hotel.HotelService.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImplementation implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        //generate unique userId
        String randomHotelId = UUID.randomUUID().toString();
        hotel.setHotelId(randomHotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found..."+ hotelId));
    }

    @Override
    public Hotel deleteHotel(String hotelId) {
        Hotel hotelToDelete = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found..."+ hotelId));
        hotelRepository.delete(hotelToDelete);
        return hotelToDelete;
    }

    @Override
    public Hotel updateHotel(String hotelId, Hotel updatedHotel) {
        //check if hotel exist
        Hotel existingHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found..."+ hotelId));
        //update fields
        existingHotel.setName(updatedHotel.getName());
        existingHotel.setAbout(updatedHotel.getAbout());
        existingHotel.setLocation(updatedHotel.getLocation());
        return hotelRepository.save(existingHotel);
    }
}
