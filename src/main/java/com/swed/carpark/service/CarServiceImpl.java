package com.swed.carpark.service;


import com.swed.carpark.entity.Car;
import com.swed.carpark.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

        @Autowired
        private CarRepository carRepository;

        @Override
        public Car saveCar(Car department) {
            return carRepository.save(department);
        }

        @Override public List<Car> getCarList() {
            return (List<Car>) carRepository.findAll();
        }

        @Override
        public Car updateCar(Car car, Integer carId) {
                return null;
        }

        @Override
        public void deleteCarById(Integer carId) {
            carRepository.deleteById(carId);
        }
}
