package com.micro.hotel.HotelService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="micro_hotels")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @Column(name="ID")
    private String hotelId;
    @Column(name="NAME")
    private String name;
    @Column(name="LOCATION")
    private String location;
    @Column(name="ABOUT")
    private String about;
}
