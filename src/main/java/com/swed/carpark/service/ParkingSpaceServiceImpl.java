package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public Integer saveSpace(ParkingSpace parkingSpace) {return parkingSpaceRepository.save(parkingSpace).getSpaceId();}


    @Override
    public List<ParkingSpace> getSpaces() {return  (List<ParkingSpace>) parkingSpaceRepository.findAll(); }

    @Override
    public boolean deleteSpaceByCarId(UUID carId) throws NoSuchElementException {
        ParkingSpace parkingSpace = parkingSpaceRepository.findOne(where(
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("carId"), carId.toString()))).get();
        parkingSpaceRepository.delete(parkingSpace);
        return true;
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
