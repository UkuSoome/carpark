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
    //private Integer floorId;
    //private Integer spaceId;

    /*@Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", weight=" + weight +
                ", height=" + height +
                ", priceperminute=" + priceperminute +
                ", floor Number=" + floorId +
                ", space Number=" + spaceId +
                '}';
    }*/
}
