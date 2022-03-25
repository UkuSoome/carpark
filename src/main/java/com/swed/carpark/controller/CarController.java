package com.swed.carpark.controller;

import com.swed.carpark.entity.Car;
import com.swed.carpark.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
public class CarController {
    @Autowired private CarService carService;

    @PostMapping("/cars")
    public HashMap<Car, String> saveCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping("/cars")
    public List<Car> fetchCarList() {
        return carService.getCarList();
    }

    @DeleteMapping("/cars/{id}")
    public String deleteCarById(@PathVariable("id") Integer carId) {
        return carService.deleteCarById(carId);
    }
}