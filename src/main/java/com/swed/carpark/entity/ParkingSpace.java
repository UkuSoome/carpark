package com.swed.carpark.entity;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PARKINGSPACES")
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="floorid")
    private Integer parkingFloorId;
    @Column(name="carid")
    private Integer carId;

    public ParkingSpace(Integer parkingFloorId, Integer carId) {
        this.parkingFloorId = parkingFloorId;
        this.carId = carId;
    }
}
