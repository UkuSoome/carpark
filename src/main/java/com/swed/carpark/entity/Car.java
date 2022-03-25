package com.swed.carpark.entity;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="CARS")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer weight;
    private Integer height;
    private double priceperminute;
    @Transient
    private Integer floorId;
    @Transient
    private Integer spaceId;
}
