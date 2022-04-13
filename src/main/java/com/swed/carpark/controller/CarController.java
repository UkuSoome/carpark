package com.swed.carpark.controller;

import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.constants.FindCarResponse;
import com.swed.carpark.constants.ParkCarResponse;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.entity.Car;
import com.swed.carpark.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class CarController {
    @Autowired private CarService carService;



    @PostMapping("/cars")
    public ParkCarResponse saveCar(@RequestBody CarDto carDto) {
        return carService.saveCar(carDto);
    }

    @GetMapping("/cars")
    public List<FindCarResponse> fetchCarList() {
        return carService.getCarList();
    }

    @DeleteMapping("/cars/{id}")
    public DeleteCarResponse deleteCarById(@PathVariable("id") UUID carId) {
        return carService.deleteCarById(carId);
    }

    @GetMapping("/cars/{id}")
    public FindCarResponse findCarByUUID(@PathVariable("id") UUID carId) { return carService.findCarByUUID(carId);}
}