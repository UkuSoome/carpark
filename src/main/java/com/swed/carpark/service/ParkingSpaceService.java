package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingSpace;
import java.util.List;

public interface ParkingSpaceService {
    ParkingSpace saveSpace(ParkingSpace parkingSpace);
    List<ParkingSpace> getSpaces();

    void deleteSpaceById(Integer spaceId);
}
