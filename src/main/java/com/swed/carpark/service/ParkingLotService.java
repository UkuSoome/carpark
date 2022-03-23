package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;
import java.util.List;

public interface ParkingLotService {

    ParkingLot saveFloor(ParkingLot parkingLotFloor);

    List<ParkingLot> getFloors();

    void deleteFloorById(Integer floorId);
}
