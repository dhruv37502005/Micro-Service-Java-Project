package com.micro.rating.RatingService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="micro_ratings")
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @Column(name="ID")
    private String ratingId;
    @Column(name="USERID")
    private String userId;
    @Column(name="HOTELID")
    private String hotelId;
    @Column(name="RATING")
    private int rating;
    @Column(name="FEEDBACK")
    private String feedback;
}
