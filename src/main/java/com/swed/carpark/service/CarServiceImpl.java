package com.swed.carpark.service;


import com.swed.carpark.entity.Car;
import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CarServiceImpl implements CarService {

        @Autowired
        private CarRepository carRepository;
        @Autowired
        private ParkingLotService parkingLotService;
        @Autowired
        private ParkingSpaceService parkingSpaceService;

        @Override
        public HashMap<Car, String> saveCar(Car car) {
            ParkingLot parkingFloor = parkingLotService.getBestSuitableFloor(car.getWeight(), car.getHeight());
            if (parkingFloor.getId() == null) {
                HashMap<Car, String> map = new HashMap<>();
                map.put(car, "No suitable parking floor found.");
                return map;
            }
            Car tempCar = carRepository.save(car);
            parkingSpaceService.saveSpace(new ParkingSpace(parkingFloor.getId(), tempCar.getId()));
            HashMap<Car, String> map = new HashMap<>();
            map.put(tempCar, "Car parked.");
            return map;
        }

        @Override
        public List<Car> getCarByWeight(Integer weight) {
               return carRepository.findAll(where(
                      (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("weight"), weight)));
        }

        @Override public List<Car> getCarList() {
            return (List<Car>) carRepository.findAll();
        }

        @Override
        public void deleteCarById(Integer carId) {
            carRepository.deleteById(carId);
        }
}
