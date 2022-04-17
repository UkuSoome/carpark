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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final BigDecimal basePrice = new BigDecimal("1.0");
    private final long freeParkingTime = 5L;

    private final CarRepository carRepository;
    private final ParkingLotService parkingLotService;
    private final ParkingSpaceService parkingSpaceService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ParkCarResponse saveCar(CarDto carDto, BigDecimal priceperminute) {
        ParkingLot floor = parkingLotService.getBestSuitableFloor(carDto.getWeight(), carDto.getHeight());
        if (floor == null) {
            return new ParkCarResponse(ParkCarStatus.NOSUITABLESPACEFOUND, null);
        }//could be http 404 error if needed. But i think returning http ok here is a bit better.
        Car car = modelMapper.map(carDto, Car.class);
        UUID uuid = UUID.randomUUID();
        priceperminute = basePrice.add(basePrice.multiply(floor.getPriceMultiplier()));
        car.setPriceperminute(priceperminute);
        car.setCarid(uuid.toString());
        car.setStarttime(LocalDateTime.now());
        try {
            carRepository.save(car); //Default function so the error has to be caught here.
        } catch(IllegalArgumentException e) {throw new DbException("Something went wrong. Car save failed.");}
        Integer spaceId = findFirstEmptySpace(floor.getNumberOfSpaces(), floor.getId()); //there probably is some better method to do this,
        if (spaceId == null) {                                                           //but i decided to save time and do it the easy way.
            spaceId = 1;
        }
        parkingSpaceService.saveSpace(new ParkingSpace(spaceId, floor.getId(), car.getCarid())); //this throws an error at the function definition so the transaction does not commit.
        return new ParkCarResponse(ParkCarStatus.PARKED, car.getCarid());
    }

    @Override public List<FindCarResponse> getCarList() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        return cars.stream()
                .map(this::carToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DeleteCarResponse deleteCarById(String carId) {
        Car car = findCarFromDb(carId);
        if (car == null) {
            return new DeleteCarResponse(DeleteCarStatus.NOSUCHCARFOUND, carId, null, null, null,null);
        }//could be http 404 error if needed. But i think returning http ok here is a bit better.
        try {
            carRepository.deleteById(car.getId()); //Default function so the error has to be caught here.
        } catch (IllegalArgumentException e) {
            throw new DbException("Something went wrong. Car not found."); // should never get here since the above findCarFromDb fails first.
        }                                                                  // But just in case some weird situation happens.
        parkingSpaceService.deleteSpaceByCarId(carId); //this throws an error at the function definition so the transaction does not commit.
        return responseWithTimeStamps(car);
    }

    @Override
    public FindCarResponse findCarByUUID(String carId) {
        Car car = findCarFromDb(carId);
        if (car == null) {
            return new FindCarResponse(FindCarStatus.CARNOTFOUND, carId, null, null, null);
        }
        return carToResponse(car);
    }

    private FindCarResponse carToResponse(Car car) {
        ParkingSpace parkingSpace = parkingSpaceService.findSpaceByCarId(car.getCarid());
        ParkingLot parkingFloor = parkingLotService.findFloorById(parkingSpace.getFloorId());
        return new FindCarResponse(FindCarStatus.CARFOUND, car.getCarid(), parkingFloor.getId(), parkingSpace.getSpaceId(), car.getPriceperminute());
    }

    private Integer findFirstEmptySpace(Integer numberOfSpaces, Integer floorId) {
        for (int i = 1; i <= numberOfSpaces; i++) {
            if (parkingSpaceService.findSpaceBySpaceId(i,floorId) == null) {
                return i;
            }
        }
        return null;
    }

    private Car findCarFromDb(String carId) {
        try {
            return carRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("carid"), carId))).get();
        } catch (NoSuchElementException e){ return null;}
    }

    private DeleteCarResponse responseWithTimeStamps(Car car) {
        LocalDateTime starttime = car.getStarttime();
        LocalDateTime endtime = LocalDateTime.now();
        Duration duration = Duration.between(starttime, endtime);
        long timeTaken = duration.toMinutes();
        long timeForPricecalculation; // separate so we can still have the timeTaken in response.
        if (timeTaken <= freeParkingTime) {
            timeForPricecalculation = 0L;
        } else {timeForPricecalculation=timeTaken-freeParkingTime;}
        BigDecimal priceForParking = car.getPriceperminute().multiply(new BigDecimal(timeForPricecalculation));
        return new DeleteCarResponse(DeleteCarStatus.DELETED, car.getCarid(), starttime, endtime, timeTaken, priceForParking);
    }
}
