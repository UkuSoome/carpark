package com.swed.carpark.service;

import com.swed.carpark.entity.Car;
import java.util.List;


public interface CarService {

    Car saveCar(Car car);

    List<Car> getCarList();

    Car updateCar(Car car, Integer carId);

    void deleteCarById(Integer carId);

}
