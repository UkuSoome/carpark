package com.swed.carpark.service;


import com.swed.carpark.constants.*;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.entity.Car;
import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        ParkingLot parkingFloor = parkingLotService.getBestSuitableFloor(carDto.getWeight(), carDto.getHeight());
        if (parkingFloor == null) {
            return new ParkCarResponse(null, ParkCarStatus.NOSUITABLESPACEFOUND);
        }
        Car car = modelMapper.map(carDto, Car.class);
        UUID uuid = UUID.randomUUID();
        priceperminute = basePrice.add(basePrice.multiply(parkingFloor.getPriceMultiplier()));
        car.setPriceperminute(priceperminute);
        car.setUuid(uuid);
        carRepository.save(car);
        parkingSpaceService.saveSpace(new ParkingSpace(parkingFloor.getId(), car.getUuid()));
        return new ParkCarResponse(uuid, ParkCarStatus.PARKED);
    }

    @Override public List<FindCarResponse> getCarList() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        return cars.stream()
                .map(car -> carToResponse(car, FindCarStatus.CARFOUND))
                .collect(Collectors.toList());
    }

    @Override
    public DeleteCarResponse deleteCarById(UUID carId) {
        try {
            Car car = carRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("uuid"), carId))).get();
            carRepository.deleteById(car.getId());
            parkingSpaceService.deleteSpaceByCarId(carId);
            return new DeleteCarResponse(carId, DeleteCarStatus.DELETED);
        }
        catch (NoSuchElementException e) {
            return new DeleteCarResponse(carId, DeleteCarStatus.NOSUCHCARFOUND);
        }
    }

    @Override
    public FindCarResponse findCarByUUID(UUID carId) {
        try {
            Car car = carRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("uuid"), carId))).get();
            return carToResponse(car, FindCarStatus.CARFOUND);
        }
        catch (NoSuchElementException e) {
            return new FindCarResponse(FindCarStatus.CARNOTFOUND, carId, null, null, null);
        }
    }

    private FindCarResponse carToResponse(Car car, FindCarStatus carStatus) {
        ParkingSpace parkingSpace = parkingSpaceService.findSpaceByCarId(car.getUuid());
        Optional<ParkingLot> parkingFloor = parkingLotService.findFloorById(parkingSpace.getParkingFloorId());
        return new FindCarResponse(carStatus, car.getUuid(), parkingFloor.get().getId(), parkingSpace.getId(), car.getPriceperminute());
    }
}
