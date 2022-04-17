package com.swed.carpark.service;

import com.swed.carpark.constants.*;
import com.swed.carpark.dto.CarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;

    private ParkCarResponse parkCarResponse;

    @BeforeEach
    void setup() {
        CarDto car = new CarDto();
        car.setWeight(351);
        car.setHeight(50);
        parkCarResponse = carService.saveCar(car, new BigDecimal(0));
    }

    @Test
    public void saveCar() { // save is done in the setup()
        assertTrue(parkCarResponse.getStatus().equals(ParkCarStatus.PARKED) && parkCarResponse.getCarId() != null);
    }
    @Test
    public void deleteCarById() {
        DeleteCarResponse deleteCarResponse= carService.deleteCarById(parkCarResponse.getCarId());
        assertTrue(deleteCarResponse.getStatus().equals(DeleteCarStatus.DELETED) && deleteCarResponse.getCarId().equals(parkCarResponse.getCarId()));
    }
    @Test
    public void findCarByUUID(){
        FindCarResponse response = carService.findCarByUUID(parkCarResponse.getCarId());
        assertTrue(response.getCarFound().equals(FindCarStatus.CARFOUND) && response.getCarid().equals(parkCarResponse.getCarId()));
    }
    @Test
    public void getCarList() {//its a weird function to test, the contents are not really important, just that it would return a list with at least 1 component
        // since we run setup() function to save a car before this test.
        List<FindCarResponse> carList = carService.getCarList();
        assertEquals(carList.get(0).getCarFound(), FindCarStatus.CARFOUND);
    }
}
