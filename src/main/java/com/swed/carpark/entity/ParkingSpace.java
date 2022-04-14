package com.swed.carpark.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PARKINGSPACES")
@IdClass(ParkingSpaceKey.class)
public class ParkingSpace implements Serializable {
    @Id
    @Column(name="spaceid")
    private Integer spaceId;
    @Id
    @Column(name="floorid")
    private Integer floorId;
    @Column(name="carid")
    private String carId;

}
