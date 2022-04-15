package com.swed.carpark.service;


import com.swed.carpark.constants.*;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.entity.Car;
import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.exception.DbException;
import com.swed.carpark.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ParkCarResponse saveCar(CarDto carDto) {
        ParkingLot floor = parkingLotService.getBestSuitableFloor(carDto.getWeight(), carDto.getHeight());
        if (floor == null) {
            return new ParkCarResponse(ParkCarStatus.NOSUITABLESPACEFOUND, null); // this could also be an http not found error.
        }
        Car car = modelMapper.map(carDto, Car.class);
        UUID uuid = UUID.randomUUID();
        priceperminute = basePrice.add(basePrice.multiply(floor.getPriceMultiplier()));
        car.setPriceperminute(priceperminute);
        car.setUuid(uuid.toString());
        try {
            carRepository.save(car);
        } catch(IllegalArgumentException e) {
            throw new DbException("Something went wrong. Car save failed.");
        }
        Integer spaceId = findFirstEmptySpace(floor.getNumberOfSpaces(), floor.getId()); //there probably is some better method to do this,
        if (spaceId == null) {                                                           //but i decided to save time and do it the easy way.
            spaceId = 1;
        }
        try {
            parkingSpaceService.saveSpace(new ParkingSpace(spaceId, floor.getId(), car.getUuid()));
        } catch(IllegalArgumentException e) {
            throw new DbException("Something went wrong. Parking space save failed.");
        }
        return new ParkCarResponse(ParkCarStatus.PARKED, uuid);
    }

    @Override public List<FindCarResponse> getCarList() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        return cars.stream()
                .map(this::carToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DeleteCarResponse deleteCarById(UUID carId) {
        Car car = findEmptyParkingSpot(carId);
        if (car == null) {
            return new DeleteCarResponse(DeleteCarStatus.NOSUCHCARFOUND, carId); // could be http 404 error if needed.
        }
        try {
            carRepository.deleteById(car.getId());
        } catch (IllegalArgumentException e) {
            throw new DbException("Something went wrong. Car not found."); // should never get here since the above findEmptyParkingSpot fails first.
        }                                                                  // But just in case some weird situation happens.
        try {
            parkingSpaceService.deleteSpaceByCarId(carId);
        } catch (NoSuchElementException e) {
            throw new DbException("Something went wrong. Parking space not found.");
        }
        return new DeleteCarResponse(DeleteCarStatus.DELETED, carId);
    }

    @Override
    public FindCarResponse findCarByUUID(UUID carId) {
        Car car = findEmptyParkingSpot(carId);
        if (car == null) {
            return new FindCarResponse(FindCarStatus.CARNOTFOUND, carId, null, null, null);
        }
        return carToResponse(car);
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

    private Car findEmptyParkingSpot(UUID carId) {
        try {
            Car car = carRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("uuid"), carId.toString()))).get();
            return car;
        } catch (NoSuchElementException e){ return null;}
    }
}
