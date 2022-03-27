package com.swed.carpark.controller;

import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.constants.FindCarResponse;
import com.swed.carpark.constants.ParkCarResponse;
import com.swed.carpark.constants.ParkCarStatus;
import com.swed.carpark.entity.Car;
import com.swed.carpark.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class CarController {
    @Autowired private CarService carService;

    @PostMapping("/cars")
    public ParkCarResponse saveCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping("/cars")
    public List<Car> fetchCarList() {
        return carService.getCarList();
    }

    @DeleteMapping("/cars/{id}")
    public DeleteCarResponse deleteCarById(@PathVariable("id") UUID carId) {
        return carService.deleteCarById(carId);
    }

    @GetMapping("/cars/{id}")
    public FindCarResponse findCarByUUID(@PathVariable("id") UUID carId) { return carService.findCarByUUID(carId);}
}