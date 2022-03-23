package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLot saveFloor(ParkingLot parkingLotFloor) { return parkingLotRepository.save(parkingLotFloor); }

    @Override
    public List<ParkingLot> getFloors() {return  (List<ParkingLot>) parkingLotRepository.findAll(); }

    @Override
    public void deleteFloorById(Integer floorId) { parkingLotRepository.deleteById(floorId); }
}
