package com.swed.carpark.service;


import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;


@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

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
            return null;
        }
    }
    @Override
    public Optional<ParkingLot> findFloorById(Integer id) {
        return parkingLotRepository.findById(id);
    }
}
