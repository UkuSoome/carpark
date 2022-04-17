package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingSpace;
import java.util.List;

public interface ParkingSpaceService {

    ParkingSpace saveSpace(ParkingSpace parkingSpace);

    List<ParkingSpace> getSpaces();

    ParkingSpace deleteSpaceByCarId(String carId);

    ParkingSpace findSpaceByCarId(String carId);

    List<ParkingSpace> findSpacesByFloorId(Integer floorId);

    ParkingSpace findSpaceBySpaceId(Integer spaceId, Integer floorId);
}
