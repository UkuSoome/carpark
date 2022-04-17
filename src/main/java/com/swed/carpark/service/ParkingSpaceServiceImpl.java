package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.exception.DbException;
import com.swed.carpark.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public ParkingSpace saveSpace(ParkingSpace parkingSpace) throws IllegalArgumentException {
        try {
            return parkingSpaceRepository.save(parkingSpace);
        } catch (IllegalArgumentException e) {
            throw new DbException("Something went wrong. Parking space save failed.");
        }
    }


    @Override
    public List<ParkingSpace> getSpaces() {return  (List<ParkingSpace>) parkingSpaceRepository.findAll(); }

    @Override
    public ParkingSpace deleteSpaceByCarId(String carId) {
        try {
            ParkingSpace parkingSpace = parkingSpaceRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("carId"), carId))).get();
            parkingSpaceRepository.delete(parkingSpace);
            return parkingSpace;
        } catch (NoSuchElementException e) {
            throw new DbException("Something went wrong. Parking space not found.");
        }
    }
    @Override
    public ParkingSpace findSpaceByCarId(String carId) {
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

    @Override
    public List<ParkingSpace> findSpacesByFloorId(Integer floorId) {
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findAll(where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("floorId"), floorId)));
        return parkingSpaces;
    }

    @Override
    public ParkingSpace findSpaceBySpaceId(Integer spaceId, Integer floorId) {
        Optional<ParkingSpace> space = parkingSpaceRepository.findOne(where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("spaceId"), spaceId), criteriaBuilder.equal(root.get("floorId"), floorId)
                        )));
        return space.orElse(null);
    }
}
