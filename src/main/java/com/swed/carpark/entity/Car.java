package com.swed.carpark.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Car {
   // @Getter @Setter private Integer id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer carId;
    private Integer weight;
    private Integer height;
    private String parkingSpot;
    private float price;
}
