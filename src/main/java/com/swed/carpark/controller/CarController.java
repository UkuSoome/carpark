package com.swed.carpark.controller;

import com.swed.carpark.constants.*;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @PostMapping("/save")
    public ParkCarResponse saveCar(@Valid @RequestBody CarDto carDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ParkCarResponse(ParkCarStatus.INVALIDINPUT, null);
        }
        return carService.saveCar(carDto);
    }

    @GetMapping("/findall")
    public List<FindCarResponse> fetchCarList() {
        return carService.getCarList();
    }

    @DeleteMapping("/delete/{id}")
    public DeleteCarResponse deleteCarById(@PathVariable("id") String carId) {
        if (!UUID_REGEX_PATTERN.matcher(carId).matches()) {
            return new DeleteCarResponse(DeleteCarStatus.INVALIDUUID, null);
        }
        return carService.deleteCarById(UUID.fromString(carId));
    }

    @GetMapping("/find/{id}")
    public FindCarResponse findCarByUUID(@PathVariable("id") String carId) {
        if (!UUID_REGEX_PATTERN.matcher(carId).matches()) {
            return new FindCarResponse(FindCarStatus.INVALIDUUID, null, null, null, null);
        }
        return carService.findCarByUUID(UUID.fromString(carId));
    }
}