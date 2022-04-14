package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import java.util.List;
import java.util.UUID;

public interface ParkingSpaceService {

    Integer saveSpace(ParkingSpace parkingSpace);

    List<ParkingSpace> getSpaces();

    boolean deleteSpaceByCarId(UUID carId);

    ParkingSpace findSpaceByCarId(String carId);

    List<ParkingSpace> findSpacesByFloorId(Integer floorId);

    ParkingSpace findSpaceBySpaceId(Integer spaceId, Integer floorId);
}
