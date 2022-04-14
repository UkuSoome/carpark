package com.swed.carpark.service;


import com.swed.carpark.constants.*;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.entity.Car;
import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private BigDecimal basePrice = new BigDecimal("1.0");
    private BigDecimal priceperminute;


    private final CarRepository carRepository;
    private final ParkingLotService parkingLotService;
    private final ParkingSpaceService parkingSpaceService;
    private final ModelMapper modelMapper;

    @Override
    public ParkCarResponse saveCar(CarDto carDto) {
        ParkingLot floor = parkingLotService.getBestSuitableFloor(carDto.getWeight(), carDto.getHeight());
        if (floor == null) {
            return new ParkCarResponse(ParkCarStatus.NOSUITABLESPACEFOUND, null);
        }
        Car car = modelMapper.map(carDto, Car.class);
        UUID uuid = UUID.randomUUID();
        priceperminute = basePrice.add(basePrice.multiply(floor.getPriceMultiplier()));
        car.setPriceperminute(priceperminute);
        car.setUuid(uuid.toString());
        carRepository.save(car);
        Integer spaceId = findFirstEmptySpace(floor.getNumberOfSpaces(), floor.getId()); //there probably is some better method to do this, but i decided to save time.
        if (spaceId == null) {
            spaceId = 1;
        }
        parkingSpaceService.saveSpace(new ParkingSpace(spaceId,floor.getId(), car.getUuid().toString()));
        return new ParkCarResponse(ParkCarStatus.PARKED, uuid);
    }

    @Override public List<FindCarResponse> getCarList() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        return cars.stream()
                .map(this::carToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeleteCarResponse deleteCarById(UUID carId) {
        try {
            Car car = carRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("uuid"), carId.toString()))).get();
            carRepository.deleteById(car.getId());
            parkingSpaceService.deleteSpaceByCarId(carId);
            return new DeleteCarResponse(DeleteCarStatus.DELETED, carId);
        }
        catch (NoSuchElementException e) {
            return new DeleteCarResponse(DeleteCarStatus.NOSUCHCARFOUND, carId);
        }
    }

    @Override
    public FindCarResponse findCarByUUID(UUID carId) {
        try {
            Car car = carRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("uuid"), carId.toString()))).get();
            return carToResponse(car);
        }
        catch (NoSuchElementException e) {
            return new FindCarResponse(FindCarStatus.CARNOTFOUND, carId, null, null, null);
        }
    }

    private FindCarResponse carToResponse(Car car) {
        ParkingSpace parkingSpace = parkingSpaceService.findSpaceByCarId(car.getUuid());
        ParkingLot parkingFloor = parkingLotService.findFloorById(parkingSpace.getFloorId());
        return new FindCarResponse(FindCarStatus.CARFOUND, UUID.fromString(car.getUuid()), parkingFloor.getId(), parkingSpace.getSpaceId(), car.getPriceperminute());
    }

    private Integer findFirstEmptySpace(Integer numberOfSpaces, Integer floorId) {
        for (int i = 1; i <= numberOfSpaces; i++) {
            if (parkingSpaceService.findSpaceBySpaceId(i,floorId) == null) {
                return i;
            }
        }
        return null;
    }
}
