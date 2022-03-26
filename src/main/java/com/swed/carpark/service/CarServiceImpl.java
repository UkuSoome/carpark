package com.swed.carpark.service;


import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.constants.DeleteCarStatus;
import com.swed.carpark.constants.ParkCarResponse;
import com.swed.carpark.constants.ParkCarStatus;
import com.swed.carpark.entity.Car;
import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CarServiceImpl implements CarService {
        private double basePrice = 1.0;
        private double priceperminute;
    @Autowired
        private CarRepository carRepository;
        @Autowired
        private ParkingLotService parkingLotService;
        @Autowired
        private ParkingSpaceService parkingSpaceService;

        @Override
        public ParkCarResponse saveCar(Car car) {
            ParkingLot parkingFloor = parkingLotService.getBestSuitableFloor(car.getWeight(), car.getHeight());
            UUID uuid = UUID.randomUUID();
            if (parkingFloor.getId() == null) {
                return new ParkCarResponse(uuid, ParkCarStatus.NOSUITABLESPACEFOUND);
            }
            priceperminute = basePrice + (basePrice * parkingFloor.getPriceMultiplier());
            car.setPriceperminute(priceperminute);
            car.setUuid(uuid);
            carRepository.save(car);
            Integer spaceId = parkingSpaceService.saveSpace(new ParkingSpace(parkingFloor.getId(), car.getUuid()));
            car.setFloorId(parkingFloor.getId());
            car.setSpaceId(spaceId);
            return new ParkCarResponse(uuid, ParkCarStatus.PARKED);
        }

        @Override public List<Car> getCarList() {
            return (List<Car>) carRepository.findAll();
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
}
