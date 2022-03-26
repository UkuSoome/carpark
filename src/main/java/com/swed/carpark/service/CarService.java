package com.swed.carpark.service;

import com.swed.carpark.constants.DeleteCarResponse;
import com.swed.carpark.constants.ParkCarResponse;
import com.swed.carpark.entity.Car;
import java.util.List;
import java.util.UUID;


public interface CarService {
    ParkCarResponse saveCar(Car car);

    List<Car> getCarList();

    DeleteCarResponse deleteCarById(UUID carId);
}
