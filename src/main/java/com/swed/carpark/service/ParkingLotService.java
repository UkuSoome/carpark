package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;
import java.util.List;

public interface ParkingLotService {
    ParkingLot saveFloor(ParkingLot parkingLotFloor);

    ParkingLot getBestSuitableFloor(Integer weight, Integer height);
}
