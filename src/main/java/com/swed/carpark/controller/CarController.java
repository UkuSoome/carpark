package com.swed.carpark.controller;

import com.swed.carpark.constants.*;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.exception.UuidException;
import com.swed.carpark.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @PostMapping("/save")
    public ResponseEntity<?> saveCar(@Valid @RequestBody CarDto carDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ParkCarResponse(ParkCarStatus.INVALIDINPUT, null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(carService.saveCar(carDto), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<?> fetchCarList() {
        return new ResponseEntity<>(carService.getCarList(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable("id") String carId) {
        if (!UUID_REGEX_PATTERN.matcher(carId).matches()) {
            throw new UuidException("Invalid UUID format.");
        }
        DeleteCarResponse response = carService.deleteCarById(carId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findCarByUUID(@PathVariable("id") String carId) {
        if (!UUID_REGEX_PATTERN.matcher(carId).matches()) {
            throw new UuidException("Invalid UUID format.");
        }
        return new ResponseEntity<>(carService.findCarByUUID(carId), HttpStatus.OK);
    }
}