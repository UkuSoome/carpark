package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;

import java.util.List;
import java.util.Optional;

public interface ParkingLotService {
    ParkingLot saveFloor(ParkingLot parkingLotFloor);

    Optional<ParkingLot> findFloorById(Integer floorId);

    ParkingLot getBestSuitableFloor(Integer weight, Integer height);
}
