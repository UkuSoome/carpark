package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.data.jpa.domain.Specification.where;


@Service

public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLot saveFloor(ParkingLot parkingLotFloor) { return parkingLotRepository.save(parkingLotFloor); }

    @Override
    public ParkingLot getBestSuitableFloor(Integer weight, Integer height) {
        List<ParkingLot> floors = parkingLotRepository.findAll(where(
                (root, query, criteriaBuilder) -> criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("weightLim"), weight),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("heightLim"), height)
        )));
        try {
            return floors.stream().min(Comparator.comparing(ParkingLot::getPriceMultiplier)).get();
        }
        catch (NoSuchElementException e) {
            return new ParkingLot();
        }
    }

    @Override
    public List<ParkingLot> getFloors() {return  (List<ParkingLot>) parkingLotRepository.findAll(); }

    @Override
    public void deleteFloorById(Integer floorId) { parkingLotRepository.deleteById(floorId); }
}
