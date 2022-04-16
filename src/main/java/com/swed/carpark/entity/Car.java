package com.swed.carpark.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="CARS")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="carid")
    private String carid;
    @Column(name="weight")
    private Integer weight;
    @Column(name="height")
    private Integer height;
    @Column(name="priceperminute")
    private BigDecimal priceperminute;
    @Column(name="starttime")
    private LocalDateTime starttime;
}
