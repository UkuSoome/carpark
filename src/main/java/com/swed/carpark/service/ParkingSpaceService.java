package com.swed.carpark.service;


import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.entity.ParkingSpace;
import java.util.List;
import java.util.UUID;

public interface ParkingSpaceService {

    Integer saveSpace(ParkingSpace parkingSpace);

    List<ParkingSpace> getSpaces();

    void deleteSpaceByCarId(UUID carId);
}
