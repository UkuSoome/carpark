package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;

public interface ParkingLotService {
    ParkingLot saveFloor(ParkingLot parkingLotFloor);

    ParkingLot findFloorById(Integer floorId);

    ParkingLot getBestSuitableFloor(Integer weight, Integer height);
}
