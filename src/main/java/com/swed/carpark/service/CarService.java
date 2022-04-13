package com.swed.carpark.service;

import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.constants.FindCarResponse;
import com.swed.carpark.constants.ParkCarResponse;
import com.swed.carpark.dto.CarDto;
import com.swed.carpark.entity.Car;
import java.util.List;
import java.util.UUID;


public interface CarService {
    ParkCarResponse saveCar(CarDto cardto);

    List<FindCarResponse> getCarList();

    DeleteCarResponse deleteCarById(UUID carId);

    FindCarResponse findCarByUUID(UUID carId);
}
