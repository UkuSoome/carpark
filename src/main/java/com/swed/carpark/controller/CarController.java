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
@RequestMapping("/cars")
public class CarController {
    @Autowired private CarService carService;


    @PostMapping("/save")
    public ParkCarResponse saveCar(@RequestBody CarDto carDto) {
        return carService.saveCar(carDto);
    }

    @GetMapping("/findall")
    public List<FindCarResponse> fetchCarList() {
        return carService.getCarList();
    }

    @DeleteMapping("/delete/{id}")
    public DeleteCarResponse deleteCarById(@PathVariable("id") UUID carId) {
        return carService.deleteCarById(carId);
    }

    @GetMapping("/find/{id}")
    public FindCarResponse findCarByUUID(@PathVariable("id") UUID carId) { return carService.findCarByUUID(carId);}
}