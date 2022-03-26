package com.swed.carpark.entity;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

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
    private UUID carId;

    public ParkingSpace(Integer parkingFloorId, UUID carId) {
        this.parkingFloorId = parkingFloorId;
        this.carId = carId;
    }
}
