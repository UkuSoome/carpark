package com.swed.carpark.service;

import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.constants.FindCarResponse;
import com.swed.carpark.constants.ParkCarResponse;
import com.swed.carpark.dto.CarDto;

import java.math.BigDecimal;
import java.util.List;


public interface CarService {
    ParkCarResponse saveCar(CarDto cardto);

    List<FindCarResponse> getCarList();

    DeleteCarResponse deleteCarById(String carId);

    FindCarResponse findCarByUUID(String carId);
}
