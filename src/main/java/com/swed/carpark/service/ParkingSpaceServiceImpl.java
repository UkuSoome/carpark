package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public Integer saveSpace(ParkingSpace parkingSpace) {return parkingSpaceRepository.save(parkingSpace).getId();}


    @Override
    public List<ParkingSpace> getSpaces() {return  (List<ParkingSpace>) parkingSpaceRepository.findAll(); }

    @Override
    public boolean deleteSpaceByCarId(UUID carId) {
        try {
            ParkingSpace parkingSpace = parkingSpaceRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("carId"), carId))).get();
            parkingSpaceRepository.deleteById(parkingSpace.getId());
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
    @Override
    public ParkingSpace findSpaceByCarId(UUID carId) {
        try {
            ParkingSpace parkingSpace = parkingSpaceRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("carId"), carId))).get();
            return parkingSpace;
        }
        catch (NoSuchElementException e) {
            return new ParkingSpace();
        }
    }
}
