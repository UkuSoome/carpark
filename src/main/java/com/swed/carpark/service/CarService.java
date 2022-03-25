package com.swed.carpark.service;

import com.swed.carpark.entity.Car;

import java.util.HashMap;
import java.util.List;


public interface CarService {
    HashMap<Car, String> saveCar(Car car);

    List<Car> getCarList();

    void deleteCarById(Integer carId);

}
