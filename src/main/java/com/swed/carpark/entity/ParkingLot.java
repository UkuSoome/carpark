package com.swed.carpark.entity;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="PARKINGLOT")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="weightlim")
    private Integer weightLim;
    @Column(name="heightlim")
    private Integer heightLim;
    @Column(name="pricemultiplier")
    private double priceMultiplier;
}
