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

public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer parkingFloorId;
    private Integer carId;
}
